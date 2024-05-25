
package acme.features.administrator.banner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Administrator;
import acme.client.services.AbstractService;
import acme.entities.banner.Banner;

@Service
public class AdministratorBannerDeleteService extends AbstractService<Administrator, Banner> {
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

	}

	@Override
	public void perform(final Banner object) {
		assert object != null;

		this.repository.delete(object);
	}

}
