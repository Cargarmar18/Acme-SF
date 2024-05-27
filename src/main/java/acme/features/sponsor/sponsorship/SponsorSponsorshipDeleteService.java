/*
 * EmployerDutyDeleteService.java
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

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.project.Project;
import acme.entities.sponsorships.Invoice;
import acme.entities.sponsorships.Sponsorship;
import acme.entities.sponsorships.SponsorshipType;
import acme.features.sponsor.invoice.SponsorInvoiceRepository;
import acme.roles.Sponsor;

@Service
public class SponsorSponsorshipDeleteService extends AbstractService<Sponsor, Sponsorship> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private SponsorSponsorshipRepository	repository;

	@Autowired
	private SponsorInvoiceRepository		invoiceRepository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int sponsorshipId;

		Sponsorship sponsorship;

		sponsorshipId = super.getRequest().getData("id", int.class);
		sponsorship = this.repository.findOneSponsorshipById(sponsorshipId);
		status = sponsorship.isDraftMode() && sponsorship != null && super.getRequest().getPrincipal().hasRole(sponsorship.getSponsor());

		super.getResponse().setAuthorised(status);

	}

	@Override
	public void load() {
		int id;

		Sponsorship object;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneSponsorshipById(id);
		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Sponsorship object) {
		assert object != null;

		super.bind(object, "code", "moment", "startSponsor", "endSponsor", "amount", "email", "moreInfo");
	}

	@Override
	public void validate(final Sponsorship object) {
		assert object != null;
		if (!super.getBuffer().getErrors().hasErrors("code"))
			super.state(object.isDraftMode() == true, "code", "sponsor.sponsorship.form.error.draftMode");

		if (!super.getBuffer().getErrors().hasErrors("code"))
			super.state(this.repository.countUnfinishedInvoicesBySponsorshipId(object.getId()) == 0, "draftMode", "sponsor.sponsorship.form.error.deleteWithPublishedInvoices");

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Collection<Invoice> invoices;

			boolean allInvoicesPublished;

			invoices = this.repository.findAllInvoicesBySponsorshipId(object.getId());
			allInvoicesPublished = invoices.stream().filter(i -> i.isDraftMode() == false).count() == invoices.size();
			if (!allInvoicesPublished)
				super.state(allInvoicesPublished, "*", "sponsor.sponsorship.form.error.publishedInvoices");

		}
	}

	@Override
	public void perform(final Sponsorship object) {
		assert object != null;
		int sponsorshipId;

		Collection<Invoice> invoices;

		sponsorshipId = object.getId();
		invoices = this.repository.findAllInvoicesBySponsorshipId(sponsorshipId);

		this.repository.deleteAll(invoices);
		this.repository.delete(object);
	}

	@Override
	public void unbind(final Sponsorship object) {
		assert object != null;
		Dataset dataset;
		SelectChoices choices;
		SelectChoices projects;

		Collection<Project> publishedProjects = this.repository.findAllDraftModeProjects();
		projects = SelectChoices.from(publishedProjects, "code", object.getProject());

		choices = SelectChoices.from(SponsorshipType.class, object.getSponsorshipType());

		dataset = super.unbind(object, "code", "startSponsor", "endSponsor", "sponsorshipType", "amount", "email", "moreInfo", "draftMode");
		dataset.put("types", choices);

		dataset.put("project", projects.getSelected().getKey());
		dataset.put("projects", projects);

		super.getResponse().addData(dataset);
	}

}
