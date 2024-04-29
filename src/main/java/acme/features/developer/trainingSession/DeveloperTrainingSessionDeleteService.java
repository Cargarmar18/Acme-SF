
package acme.features.developer.trainingSession;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.trainingModules.TrainingModule;
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

		if (!super.getBuffer().getErrors().hasErrors("draftMode"))
			super.state(object.isDraftMode() == true, "draftMode", "developer.training-session.form.error.draftMode");

	}

	@Override
	public void perform(final TrainingSession object) {
		assert object != null;

		this.repository.delete(object);
	}

	@Override
	public void unbind(final TrainingSession object) {
		assert object != null;
		SelectChoices choices;
		Collection<TrainingModule> trainingModules;
		int developerId;

		Dataset dataset;

		developerId = super.getRequest().getPrincipal().getActiveRoleId();
		trainingModules = this.repository.findManyTrainingModulesDraftModeByDeveloperId(developerId);
		choices = SelectChoices.from(trainingModules, "code", object.getTrainingModule());

		dataset = super.unbind(object, "code", "beginningMoment", "endMoment", "location", "instructor", "contactEmail", "link", "draftMode", "trainingModule");
		dataset.put("trainingModule", choices.getSelected().getKey());
		dataset.put("trainingModules", choices);

		super.getResponse().addData(dataset);
	}

}
