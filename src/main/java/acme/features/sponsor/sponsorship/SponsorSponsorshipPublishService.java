/*
 * EmployerJobPublishService.java
 *
 * Copyright (C) 2012-2024 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.sponsor.sponsorship;

import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.project.Project;
import acme.entities.sponsorships.Invoice;
import acme.entities.sponsorships.Sponsorship;
import acme.entities.sponsorships.SponsorshipType;
import acme.roles.Sponsor;

@Service
public class SponsorSponsorshipPublishService extends AbstractService<Sponsor, Sponsorship> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private SponsorSponsorshipRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean drafted;
		int id;
		Sponsor sponsor;
		Sponsorship sponsorship;

		id = super.getRequest().getData("id", int.class);
		sponsorship = this.repository.findOneSponsorshipById(id);
		sponsor = sponsorship == null ? null : sponsorship.getSponsor();
		drafted = sponsorship != null && sponsorship.isDraftMode() && super.getRequest().getPrincipal().hasRole(sponsor);

		super.getResponse().setAuthorised(drafted);
	}

	@Override
	public void load() {
		Sponsorship object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneSponsorshipById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Sponsorship object) {
		assert object != null;
		super.bind(object, "code", "startSponsor", "endSponsor", "sponsorshipType", "amount", "email", "moreInfo", "project");
	}

	@Override
	public void validate(final Sponsorship object) {
		assert object != null;

		Date aboveMoment = MomentHelper.parse("2201/01/01 00:00", "yyyy/MM/dd HH:mm");

		String acceptedCurrencies = this.repository.findConfiguration().getAcceptedCurrencies();
		List<String> acceptedCurrencyList = Arrays.asList(acceptedCurrencies.split("\\s*;\\s*"));

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Sponsorship sponsorshipValid;
			sponsorshipValid = this.repository.findOneSponsorshipByCode(object.getCode());
			super.state(sponsorshipValid == null || sponsorshipValid.equals(object), "code", "sponsor.sponsorship.form.error.duplicate");
		}

		if (object.getStartSponsor() != null) {

			if (!super.getBuffer().getErrors().hasErrors("startSponsor")) {
				super.state(MomentHelper.isAfter(object.getStartSponsor(), object.getMoment()), "startSponsor", "sponsor.sponsorship.form.error.startSponsor");

				super.state(MomentHelper.isBefore(object.getStartSponsor(), aboveMoment), "startSponsor", "sponsor.sponsorship.form.error.startSponsorshipAboveLimit");

			}

			if (!super.getBuffer().getErrors().hasErrors("endSponsor")) {

				super.state(MomentHelper.isBefore(object.getEndSponsor(), aboveMoment), "endSponsor", "sponsor.sponsorship.form.error.endSponsorshipAboveLimit");

				super.state(MomentHelper.isAfter(object.getEndSponsor(), object.getMoment()), "endSponsor", "sponsor.sponsorship.form.error.endSponsor");

				if (!super.getBuffer().getErrors().hasErrors("startSponsor")) {
					super.state(MomentHelper.isBefore(object.getStartSponsor(), object.getEndSponsor()), "startSponsor", "sponsor.sponsorship.form.error.startSponsorBeforeendSponsor");
					super.state(MomentHelper.isLongEnough(object.getStartSponsor(), object.getEndSponsor(), 30, ChronoUnit.DAYS), "endSponsor", "sponsor.sponsorship.form.error.period");

				}
			}
		}

		if (!super.getBuffer().getErrors().hasErrors("amount")) {

			super.state(object.getAmount().getAmount() <= 1000000.00 && object.getAmount().getAmount() >= 0.00, "amount", "sponsor.sponsorship.form.error.amountOutOfLImits");

			super.state(acceptedCurrencyList.contains(object.getAmount().getCurrency()), "amount", "sponsor.sponsorship.form.error.currencyNotSupported");
		}

		if (!super.getBuffer().getErrors().hasErrors("draftMode"))
			super.state(object.isDraftMode() == true, "code", "sponsor.sponsorship.form.error.draftMode");

		if (!super.getBuffer().getErrors().hasErrors("amount")) {
			Collection<Invoice> invoices;
			double sponsorshipAmount;
			double invoicesTotalAmount;
			boolean allInvoicesPublished;

			invoices = this.repository.findAllInvoicesBySponsorshipId(object.getId());

			sponsorshipAmount = object.getAmount().getAmount();
			invoicesTotalAmount = invoices.stream().mapToDouble(i -> i.totalAmount().getAmount()).sum();
			allInvoicesPublished = invoices.stream().filter(i -> i.isDraftMode() == false).count() == invoices.size();
			if (!allInvoicesPublished)
				super.state(allInvoicesPublished, "*", "sponsor.sponsorship.form.error.publishedInvoices");

			super.state(sponsorshipAmount == invoicesTotalAmount, "*", "sponsor.sponsorship.form.error.valuesDifference");

			int allInvoices = this.repository.countAllInvoicesBySponsorshipId(object.getId());
			super.state(allInvoices != 0, "*", "sponsor.sponsorship.form.error.unfinishedInvoices");

		}
	}

	@Override
	public void perform(final Sponsorship object) {
		assert object != null;
		object.setDraftMode(false);
		this.repository.save(object);
	}

	@Override
	public void unbind(final Sponsorship object) {
		assert object != null;
		Dataset dataset;
		SelectChoices sponsorshipTypes;
		SelectChoices projects;

		Collection<Project> publishedProjects = this.repository.findAllDraftModeProjects();
		projects = SelectChoices.from(publishedProjects, "code", object.getProject());

		sponsorshipTypes = SelectChoices.from(SponsorshipType.class, object.getSponsorshipType());

		dataset = super.unbind(object, "code", "moment", "startSponsor", "endSponsor", "sponsorshipType", "amount", "email", "moreInfo", "draftMode");

		dataset.put("types", sponsorshipTypes);

		dataset.put("project", projects.getSelected().getKey());
		dataset.put("projects", projects);

		super.getResponse().addData(dataset);
	}

}
