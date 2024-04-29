/*
 * EmployerJobCreateService.java
 *
 * Copyright (C) 2012-2024 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.sponsor.invoice;

import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.sponsorships.Invoice;
import acme.entities.sponsorships.Sponsorship;
import acme.roles.Sponsor;

@Service
public class SponsorInvoiceCreateService extends AbstractService<Sponsor, Invoice> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private SponsorInvoiceRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Invoice object;
		Date moment;
		long aDayInMs = 24 * 60 * 60 * 1000;
		moment = MomentHelper.getCurrentMoment();
		Date dueDate = new Date(moment.getTime() + aDayInMs * 31);

		object = new Invoice();

		object.setRegistrationTime(moment);
		object.setDueDate(dueDate);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Invoice object) {

		assert object != null;

		int sponsorshipId;
		Sponsorship sponsorship;

		sponsorshipId = super.getRequest().getData("sponsorship", int.class);
		sponsorship = this.repository.findOneSponsorshipById(sponsorshipId);
		object.setSponsorship(sponsorship);

		super.bind(object, "code", "link", "dueDate", "quantity", "tax");

	}

	@Override
	public void validate(final Invoice object) {

		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Invoice invoiceSameCode;
			invoiceSameCode = this.repository.findOneInvoiceByCode(object.getCode());
			super.state(invoiceSameCode == null, "code", "sponsor.invoice.form.error.duplicate");
		}

		if (!super.getBuffer().getErrors().hasErrors("sponsorship"))
			super.state(object.getSponsorship().isDraftMode() == true, "sponsorship", "sponsor.invoice.form.error.sponsorship");

		if (!super.getBuffer().getErrors().hasErrors("dueDate"))
			super.state(MomentHelper.isAfter(object.getDueDate(), object.getRegistrationTime()), "dueDate", "sponsor.invoice.form.error.dueDate");

		if (!super.getBuffer().getErrors().hasErrors("dueDate"))
			super.state(MomentHelper.isLongEnough(object.getRegistrationTime(), object.getDueDate(), 1, ChronoUnit.MONTHS), "dueDate", "sponsor.invoice.form.error.period");

		if (!super.getBuffer().getErrors().hasErrors("InvoiceQuantity"))
			super.state(object.getInvoiceQuantity() != null && object.getInvoiceQuantity().getAmount() <= 1000000.00 && object.getInvoiceQuantity().getAmount() >= -1000000.00, "quantity", "sponsor.invoice.form.error.amountOutOfBounds");

		if (!super.getBuffer().getErrors().hasErrors("InvoiceQuantity"))
			super.state(object.getSponsorship() != null && object.getInvoiceQuantity() != null && object.getInvoiceQuantity().getCurrency().equals(object.getSponsorship().getAmount().getCurrency()), "quantity", "sponsor.invoice.form.error.currency");

	}

	@Override
	public void perform(final Invoice object) {
		assert object != null;
		this.repository.save(object);
	}

	@Override
	public void unbind(final Invoice object) {

		assert object != null;

		Dataset dataset;
		SelectChoices sponsorships;
		int sponsorId = super.getRequest().getPrincipal().getActiveRoleId();

		Collection<Sponsorship> unpublishedSponsorships = this.repository.findSponsorDraftModeSponsorship(sponsorId);
		sponsorships = SelectChoices.from(unpublishedSponsorships, "code", object.getSponsorship());

		dataset = super.unbind(object, "code", "link", "registrationTime", "dueDate", "quantity", "tax", "published");

		dataset.put("sponsorship", sponsorships.getSelected().getKey());
		dataset.put("sponsorships", sponsorships);

		super.getResponse().addData(dataset);
	}

}
