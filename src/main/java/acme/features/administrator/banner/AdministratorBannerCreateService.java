
package acme.features.administrator.banner;

import java.time.temporal.ChronoUnit;

/*
 * AdministratorBannerCreateService.java
 *
 * Copyright (C) 2012-2024 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Administrator;
import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.entities.banner.Banner;

@Service
public class AdministratorBannerCreateService extends AbstractService<Administrator, Banner> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AdministratorBannerRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Banner object;

		object = new Banner();

		Date moment;
		moment = MomentHelper.getCurrentMoment();
		object.setInstatiationUpdateMoment(moment);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Banner object) {
		assert object != null;

		super.bind(object, "instantiationUpdateMoment", "startDisplay", "endDisplay", "pictureLink", "slogan", "targetLink");
	}

	@Override
	public void validate(final Banner object) {
		assert object != null;
		Date creationMoment = object.getInstatiationUpdateMoment();
		Date lowerLimit = MomentHelper.parse("2000/01/01 00:00", "yyyy/MM/dd HH:mm");
		Date upperLimit = MomentHelper.parse("2200/12/31 23:59", "yyyy/MM/dd HH:mm");
		Date startUpper = MomentHelper.parse("2200/12/24 23:59", "yyyy/MM/dd HH:mm");

		if (object.getStartDisplay() != null) {
			if (!super.getBuffer().getErrors().hasErrors("startDisplay"))
				super.state(MomentHelper.isAfterOrEqual(object.getStartDisplay(), creationMoment), "startDisplay", "administrator.banner.form.error.afterInstantiation");

			if (!super.getBuffer().getErrors().hasErrors("startDisplay"))
				super.state(MomentHelper.isAfterOrEqual(object.getStartDisplay(), lowerLimit), "startDisplay", "administrator.banner.form.error.dateOutOfBounds");

			if (!super.getBuffer().getErrors().hasErrors("startDisplay"))
				super.state(MomentHelper.isBeforeOrEqual(object.getStartDisplay(), startUpper), "startDisplay", "administrator.banner.form.error.dateOutOfBounds");
			if (object.getEndDisplay() != null)
				if (!super.getBuffer().getErrors().hasErrors("endDisplay")) {
					Date minimumEnd;

					minimumEnd = MomentHelper.deltaFromMoment(object.getStartDisplay(), 7, ChronoUnit.DAYS);
					super.state(MomentHelper.isAfterOrEqual(object.getEndDisplay(), minimumEnd), "endDisplay", "administrator.banner.form.error.bannerPeriod");
				}
		}
		if (object.getEndDisplay() != null) {
			if (!super.getBuffer().getErrors().hasErrors("endDisplay"))
				super.state(MomentHelper.isBeforeOrEqual(object.getEndDisplay(), upperLimit), "endDisplay", "administrator.banner.form.error.dateOutOfBounds");

			if (!super.getBuffer().getErrors().hasErrors("endDisplay"))
				super.state(MomentHelper.isAfterOrEqual(object.getEndDisplay(), lowerLimit), "endDisplay", "administrator.banner.form.error.dateOutOfBounds");
		}
	}

	@Override
	public void perform(final Banner object) {
		assert object != null;

		Date moment;
		moment = MomentHelper.getCurrentMoment();
		object.setInstatiationUpdateMoment(moment);

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
