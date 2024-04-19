
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

package acme.features.administrator.banner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Administrator;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.banner.Banner;
import acme.roles.Manager;

@Service
public class AdministratorBannerUpdateService extends AbstractService<Administrator, Banner> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AdministratorBannerRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int BannerId;
		Banner Banner;
		Manager manager;

		BannerId = super.getRequest().getData("id", int.class);
		Banner = this.repository.findBannerById(BannerId);
		// status = Banner != null && Banner.isDraftMode() && super.getRequest().getPrincipal().hasRole(manager);

		super.getResponse();
	}

	@Override
	public void load() {
		Banner object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findBannerById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Banner object) {
		assert object != null;

		super.bind(object, "code", "title", "abstractDescription", "indication", "cost", "link");
	}

	@Override
	public void validate(final Banner object) {
		assert object != null;

		//if (!super.getBuffer().getErrors().hasErrors("draftMode"))
		// super.state(object.isDraftMode() == true, "draftMode", "manager.user-story.form.error.draftMode");
	}

	@Override
	public void perform(final Banner object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Banner object) {
		assert object != null;
		Dataset dataset;

		dataset = super.unbind(object, "code", "title", "abstractDescription", "indication", "cost", "link", "draftMode");

		super.getResponse().addData(dataset);
	}

}
