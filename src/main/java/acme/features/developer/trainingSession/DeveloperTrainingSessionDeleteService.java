
package acme.features.developer.trainingSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.trainingModules.TrainingSession;
import acme.roles.Developer;

@Service
public class DeveloperTrainingSessionDeleteService extends AbstractService<Developer, TrainingSession> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private DeveloperTrainingSessionRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int id;
		TrainingSession trainingSession;
		Developer developer;

		id = super.getRequest().getData("id", int.class);
		trainingSession = this.repository.findOneTrainingSessionById(id);
		developer = trainingSession == null ? null : trainingSession.getTrainingModule().getDeveloper();
		status = trainingSession != null && trainingSession.isDraftMode() && super.getRequest().getPrincipal().hasRole(developer) && developer.getId() == super.getRequest().getPrincipal().getActiveRoleId();

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		TrainingSession object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneTrainingSessionById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final TrainingSession object) {
		assert object != null;

		super.unbind(object, "code", "beginningMoment", "endMoment", "location", "instructor", "contactEmail", "link", "trainingModule");
	}

	@Override
	public void validate(final TrainingSession object) {
		assert object != null;

	}

	@Override
	public void perform(final TrainingSession object) {
		assert object != null;

		this.repository.delete(object);
	}

}
