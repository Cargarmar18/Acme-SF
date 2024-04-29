
package acme.features.developer.trainingSession;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.entities.trainingModules.TrainingSession;
import acme.roles.Developer;

@Controller
public class DeveloperTrainingSessionController extends AbstractController<Developer, TrainingSession> {
	// Internal state ---------------------------------------------------------

	@Autowired
	private DeveloperTrainingSessionListMineService	listMineService;

	@Autowired
	private DeveloperTrainingSessionListService		listService;

	@Autowired
	private DeveloperTrainingSessionShowService		showService;

	@Autowired
	private DeveloperTrainingSessionCreateService	createService;

	@Autowired
	private DeveloperTrainingSessionDeleteService	deleteService;

	@Autowired
	private DeveloperTrainingSessionUpdateService	updateService;

	@Autowired
	private DeveloperTrainingSessionPublishService	publishService;

	// Constructors -----------------------------------------------------------


	@PostConstruct
	protected void initialise() {

		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("create", this.createService);
		super.addBasicCommand("list", this.listService);
		super.addBasicCommand("delete", this.deleteService);
		super.addBasicCommand("update", this.updateService);

		super.addCustomCommand("list-mine", "list", this.listMineService);
		super.addCustomCommand("publish", "update", this.publishService);

	}
}
