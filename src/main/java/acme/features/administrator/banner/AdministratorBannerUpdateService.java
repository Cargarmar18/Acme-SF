
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

import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Administrator;
import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.entities.banner.Banner;

@Service
public class AdministratorBannerUpdateService extends AbstractService<Administrator, Banner> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AdministratorBannerRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		Banner object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findBannerById(id);

		super.getBuffer().addData(object);
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

		super.bind(object, "instatiationUpdateMoment", "startDisplay", "endDisplay", "pictureLink", "slogan", "targetLink");
	}

	@Override
	public void validate(final Banner object) {
		assert object != null;

		Date minimumPeriod;
		minimumPeriod = MomentHelper.deltaFromMoment(object.getStartDisplay(), 7, ChronoUnit.DAYS);

		if (!super.getBuffer().getErrors().hasErrors("instantiationUpdateMoment") && !super.getBuffer().getErrors().hasErrors("startDisplay") && !super.getBuffer().getErrors().hasErrors("endDisplay"))
			if (!MomentHelper.isBefore(object.getInstatiationUpdateMoment(), object.getStartDisplay()))
				super.state(false, "instantiationUpdateMoment", "administrator.banner.form.error.instantiationAfterDisplay");
			else if (!MomentHelper.isBefore(object.getStartDisplay(), object.getEndDisplay()))
				super.state(false, "startDisplay", "administrator.banner.form.error.initialAfterEnd");
			else if (!MomentHelper.isBeforeOrEqual(minimumPeriod, object.getEndDisplay()))
				super.state(false, "endDisplay", "administrator.banner.form.error.lessThanMin");
	}

	@Override
	public void perform(final Banner object) {
		assert object != null;

		Date currentMoment = MomentHelper.getCurrentMoment();
		Date updateMoment = new Date(currentMoment.getTime());

		object.setInstatiationUpdateMoment(updateMoment);

		this.repository.save(object);
	}

	@Override
	public void unbind(final Banner object) {
		assert object != null;
		Dataset dataset;

		dataset = super.unbind(object, "instatiationUpdateMoment", "startDisplay", "endDisplay", "pictureLink", "slogan", "targetLink");

		super.getResponse().addData(dataset);
	}

}
