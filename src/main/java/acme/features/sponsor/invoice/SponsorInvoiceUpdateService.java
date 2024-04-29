/*
 * EmployerDutyUpdateService.java
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
import acme.client.data.models.Errors;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.sponsorships.Invoice;
import acme.entities.sponsorships.Sponsorship;
import acme.roles.Sponsor;

@Service
public class SponsorInvoiceUpdateService extends AbstractService<Sponsor, Invoice> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private SponsorInvoiceRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int invoiceId;
		Invoice invoice;

		invoiceId = super.getRequest().getData("id", int.class);
		invoice = this.repository.findOneInvoiceById(invoiceId);
		status = !invoice.isDraftMode() && invoice != null && super.getRequest().getPrincipal().hasRole(invoice.getSponsorship().getSponsor());

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Invoice object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneInvoiceById(id);

		Date moment;
		moment = MomentHelper.getCurrentMoment();
		object.setRegistrationTime(moment);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Invoice object) {
		assert object != null;

		super.bind(object, "code", "link", "dueDate", "quantity", "tax", "sponsorship");
	}

	@Override
	public void validate(final Invoice object) {
		assert object != null;

		Errors errors = super.getBuffer().getErrors();

		if (!errors.hasErrors("code")) {
			Invoice invoiceSameCode = this.repository.findOneInvoiceByCode(object.getCode());
			int id = invoiceSameCode != null ? invoiceSameCode.getId() : -1;
			super.state(id == object.getId() || invoiceSameCode == null, "code", "sponsor.invoice.form.error.duplicate");
		}

		if (!errors.hasErrors("dueDate")) {
			super.state(MomentHelper.isAfter(object.getDueDate(), MomentHelper.getCurrentMoment()), "dueDate", "sponsor.invoice.form.error.dueDate");
			super.state(MomentHelper.isLongEnough(object.getRegistrationTime(), object.getDueDate(), 1, ChronoUnit.MONTHS), "dueDate", "sponsor.invoice.form.error.period");
		}

		if (!errors.hasErrors("sponsorship"))
			super.state(object.getSponsorship().isDraftMode(), "sponsorship", "sponsor.invoice.form.error.sponsorship");

		if (!errors.hasErrors("draftMode"))
			super.state(object.isDraftMode(), "draftMode", "sponsor.invoice.form.error.draftMode");

		if (!errors.hasErrors("invoiceQuantity")) {
			Double amount = object.getInvoiceQuantity().getAmount();
			super.state(amount.compareTo(Double.valueOf(1000000.00)) <= 0 && amount.compareTo(Double.valueOf(-1000000.00)) >= 0, "invoiceQuantity", "sponsor.invoice.form.error.outOfRange");
			super.state(object.getInvoiceQuantity().getCurrency().equals(object.getSponsorship().getAmount().getCurrency()), "invoiceQuantity", "sponsor.invoice.form.error.wrongCurrency");
		}

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

		Collection<Sponsorship> sponsorSponsorships = this.repository.findSponsorshipBySponsorId(sponsorId);
		sponsorships = SelectChoices.from(sponsorSponsorships, "code", object.getSponsorship());

		dataset = super.unbind(object, "code", "link", "registrationTime", "dueDate", "quantity", "tax", "published");
		Sponsorship selectedSponsorship = this.repository.findOneSponsorshipById(Integer.valueOf(sponsorships.getSelected().getKey()));
		dataset.put("sponsorship", sponsorships.getSelected().getKey());

		sponsorSponsorships = this.repository.findSponsorDraftModeSponsorship(sponsorId);
		if (!sponsorSponsorships.contains(selectedSponsorship) && selectedSponsorship != null)
			sponsorSponsorships.add(selectedSponsorship);

		sponsorships = SelectChoices.from(sponsorSponsorships, "code", object.getSponsorship());
		dataset.put("sponsorships", sponsorships);

		super.getResponse().addData(dataset);
	}

}