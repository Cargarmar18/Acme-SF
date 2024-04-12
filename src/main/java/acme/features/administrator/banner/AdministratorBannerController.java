
package acme.features.administrator.banner;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.client.data.accounts.Administrator;
import acme.entities.banner.Banner;

@Controller
public class AdministratorBannerController extends AbstractController<Administrator, Banner> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AdministratorBannerListService listService;
	// @Autowired
	//private AdministratorControllerCreateService	createService;

	// @Autowired
	//private AdministratorBannerUpdateService		updateService;

	// @Autowired
	// private AdministratorBannerDeleteService		showBanner;

	// Constructors -----------------------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("list", this.listService);
		// super.addBasicCommand("create", this.createService);
		// super.addBasicCommand("update", this.updateService);
		// super.addBasicCommand("show", this.showBanner);
	}

}
