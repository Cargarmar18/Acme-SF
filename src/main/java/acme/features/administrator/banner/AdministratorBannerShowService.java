
package acme.features.administrator.banner;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.data.accounts.Any;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.banner.Banner;

public class AdministratorBannerShowService extends AbstractService<Any, Banner> {

	@Autowired
	private AdministratorBannerRepository repository;


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		int id = super.getRequest().getData("id", int.class);
		Banner object = this.repository.findBannerById(id);
		if (object != null)
			super.getBuffer().addData(object);
		else {
			// Manejar caso cuando el Banner no se encuentra
			// Puedes lanzar una excepción o tomar alguna acción apropiada
		}
	}

	@Override
	public void unbind(final Banner object) {
		assert object != null;

		Dataset dataset = super.unbind(object, "instatiationUpdateMoment", "startDisplay", "endDisplay", "pictureLink", "slogan", "targetLink");

		super.getResponse().addData(dataset);
	}
}
