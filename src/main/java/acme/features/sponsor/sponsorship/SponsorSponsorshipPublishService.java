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
		Collection<Invoice> invoices;
		invoices = this.repository.findAllInvoicesBySponsorshipId(object.getId());

		if (!super.getBuffer().getErrors().hasErrors("amount")) {
			Double totalAmount = invoices.stream().mapToDouble(i -> i.totalAmount().getAmount()).sum();
			super.state(object.getAmount().getAmount() >= totalAmount, "amount", "sponsor.sponsorship.form.error.amountInvoices");
		}
		if (!super.getBuffer().getErrors().hasErrors("draftMode"))
			super.state(invoices.stream().allMatch(i -> !i.isDraftMode()), "draftMode", "sponsor.sponsorship.form.error.publishedInvoices");

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

		Collection<Project> unpublishedProjects = this.repository.findAllDraftModeProjects();
		projects = SelectChoices.from(unpublishedProjects, "code", object.getProject());

		sponsorshipTypes = SelectChoices.from(SponsorshipType.class, object.getSponsorshipType());

		dataset = super.unbind(object, "code", "moment", "startSponsor", "endSponsor", "amount", "email", "moreInfo");

		dataset.put("project", projects.getSelected().getKey());
		dataset.put("projects", projects);

		dataset.put("sponsorshipType", sponsorshipTypes);
		super.getResponse().addData(dataset);
	}

}
