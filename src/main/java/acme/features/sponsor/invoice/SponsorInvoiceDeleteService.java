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

package acme.features.sponsor.invoice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.sponsorships.Invoice;
import acme.roles.Sponsor;

@Service
public class SponsorInvoiceDeleteService extends AbstractService<Sponsor, Invoice> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private SponsorInvoiceRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int InvoicesId;
		Invoice Invoices;

		InvoicesId = super.getRequest().getData("id", int.class);
		Invoices = this.repository.findOneInvoiceById(InvoicesId);
		status = Invoices.isDraftMode() && Invoices != null && super.getRequest().getPrincipal().hasRole(Invoices.getSponsorship().getSponsor());

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Invoice object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneInvoiceById(id);
		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Invoice object) {
		assert object != null;

		super.bind(object, "code", "registrationTime", "dueDate", "invoiceQuantity", "tax", "link");
	}

	@Override
	public void validate(final Invoice object) {
		assert object != null;
		if (!super.getBuffer().getErrors().hasErrors("published"))
			super.state(object.isDraftMode() == false, "sponsorship", "sponsor.Invoices.form.error.published");
	}

	@Override
	public void perform(final Invoice object) {
		assert object != null;
		this.repository.delete(object);
	}

	@Override
	public void unbind(final Invoice object) {
		assert object != null;
		Dataset dataset;
		dataset = super.unbind(object, "code", "link", "registrationTime", "dueDate", "quantity", "tax", "draftMode");
		super.getResponse().addData(dataset);
	}

}
