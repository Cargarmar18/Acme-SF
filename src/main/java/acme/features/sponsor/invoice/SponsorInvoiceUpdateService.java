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
		status = invoice.isDraftMode() && invoice != null && super.getRequest().getPrincipal().hasRole(invoice.getSponsorship().getSponsor());

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

		super.bind(object, "code", "dueDate", "invoiceQuantity", "tax", "link", "invoice.sponsorship");
	}

	@Override
	public void validate(final Invoice object) {
		assert object != null;

		Date aboveMoment = MomentHelper.parse("2201/01/01 00:00", "yyyy/MM/dd HH:mm");

		Errors errors = super.getBuffer().getErrors();

		if (!errors.hasErrors("code")) {
			Invoice invoiceSameCode = this.repository.findOneInvoiceByCode(object.getCode());
			int id = invoiceSameCode != null ? invoiceSameCode.getId() : -1;
			super.state(id == object.getId() || invoiceSameCode == null, "code", "sponsor.invoice.form.error.duplicate");
		}

		if (!errors.hasErrors("dueDate")) {
			super.state(MomentHelper.isAfter(object.getDueDate(), object.getRegistrationTime()), "dueDate", "sponsor.invoice.form.error.dueDateAfterMoment");
			super.state(MomentHelper.isLongEnough(object.getRegistrationTime(), object.getDueDate(), 30, ChronoUnit.DAYS), "dueDate", "sponsor.invoice.form.error.period");
			super.state(MomentHelper.isBefore(object.getDueDate(), aboveMoment), "dueDate", "sponsor.invoide.form.error.dueDateAboveLimit");

		}

		if (!errors.hasErrors("sponsorship"))
			if (!errors.hasErrors("invoiceQuantity")) {
				super.state(object.getInvoiceQuantity().getAmount() <= object.getSponsorship().getAmount().getAmount(), "invoiceQuantity", "sponsor.invoice.form.error.outOfRange");
				super.state(object.getInvoiceQuantity().getCurrency().equals(object.getSponsorship().getAmount().getCurrency()), "invoiceQuantity", "sponsor.invoice.form.error.sponsorshipCurrency");
			}
		if (!errors.hasErrors("invoiceQuantity"))
			super.state(object.getInvoiceQuantity().getAmount() >= 0.00 && object.getInvoiceQuantity().getAmount() <= 1000000.00, "invoiceQuantity", "sponsor.invoice.form.error.outOfRange");

		if (!errors.hasErrors("draftMode"))
			super.state(object.isDraftMode(), "draftMode", "sponsor.invoice.form.error.draftMode");
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

		dataset = super.unbind(object, "code", "link", "registrationTime", "dueDate", "invoiceQuantity", "tax", "link", "draftMode");
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
