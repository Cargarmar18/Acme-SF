
package acme.features.administrator.banner;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Administrator;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.banner.Banner;

@Service
public class AdministratorBannerListService extends AbstractService<Administrator, Banner> {
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
		Collection<Banner> objects;

		objects = (Collection<Banner>) this.repository.findRandomBanner();

		super.getBuffer().addData(objects);
	}
	@Override
	public void unbind(final Banner object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "instatiationUpdateMoment", "startDisplay", "endDisplay", "pictureLink", "slogan", "targetLink");

		super.getResponse().addData(dataset);
	}

}
