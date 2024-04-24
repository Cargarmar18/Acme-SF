
import org.springframework.beans.factory.annotation.Autowired;

import acme.client.data.accounts.Administrator;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.banner.Banner;

public class AdministratorBannerDeleteService extends AbstractService<Administrator, Banner> {
	// Internal state ---------------------------------------------------------

	@Autowired
	private AdministratorBannerRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int BannerId;
		Banner Banner;
		Administrator admin;

		BannerId = super.getRequest().getData("id", int.class);
		Banner = this.repository.findBannerById(BannerId);
		status = Banner != null && super.getRequest().getPrincipal().hasRole(admin);

		super.getResponse().setAuthorised(status);
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

		super.bind(object, "code", "registrationTime", "dueDate", "invoiceQuantity", "tax", "link");
	}

	@Override
	public void validate(final Banner object) {
		assert object != null;

	}

	@Override
	public void perform(final Banner object) {
		assert object != null;

		Banner Banner;

		this.repository.delete(Banner);
		this.repository.delete(object);
	}

	@Override
	public void unbind(final Banner object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "code", "registrationTime", "dueDate", "invoiceQuantity", "tax", "link");

		super.getResponse().addData(dataset);
	}
}
