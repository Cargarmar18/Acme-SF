
package acme.features.developer.trainingModule;

import java.util.Collection;
import java.util.Date;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.project.Project;
import acme.entities.trainingModules.DifficultyLevel;
import acme.entities.trainingModules.TrainingModule;
import acme.entities.trainingModules.TrainingSession;
import acme.roles.Developer;

@Service
public class DeveloperTrainingModulePublishService extends AbstractService<Developer, TrainingModule> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private DeveloperTrainingModuleRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int trainingModuleId;
		TrainingModule trainingModule;
		Developer developer;

		trainingModuleId = super.getRequest().getData("id", int.class);
		trainingModule = this.repository.findOneTrainingModuleById(trainingModuleId);
		developer = trainingModule == null ? null : trainingModule.getDeveloper();
		status = trainingModule != null && trainingModule.isDraftMode() && super.getRequest().getPrincipal().hasRole(developer) && developer.getId() == super.getRequest().getPrincipal().getActiveRoleId();

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		TrainingModule object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneTrainingModuleById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final TrainingModule object) {
		assert object != null;

		super.bind(object, "code", "details", "difficultyLevel", "link", "totalTime", "project");

	}

	@Override
	public void validate(final TrainingModule object) {
		assert object != null;
		Date lowerLimit = MomentHelper.parse("2000/01/01 00:00", "yyyy/MM/dd HH:mm");

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			TrainingModule codeValid;
			TrainingModule current;

			current = this.repository.findOneTrainingModuleById(object.getId());
			codeValid = this.repository.findOneTrainingModuleByCode(object.getCode());
			super.state(codeValid == null || Objects.equals(object.getId(), current.getId()), "code", "developer.training-module.form.error.duplicated");
		}

		if (!super.getBuffer().getErrors().hasErrors("draftMode"))
			super.state(object.isDraftMode() == false, "draftMode", "developer.training-module.form.error.draftModePublish");

		if (object.getUpdateMoment() != null) {
			if (!super.getBuffer().getErrors().hasErrors("updateMoment"))
				super.state(MomentHelper.isAfter(object.getUpdateMoment(), object.getCreationMoment()), "updateMoment", "developer.training-module.form.error.updateMomentAfterCreationMoment");

			if (!super.getBuffer().getErrors().hasErrors("updateMoment"))
				super.state(MomentHelper.isAfter(object.getUpdateMoment(), lowerLimit), "updateMoment", "developer.training-module.form.error.updateMomentBeforeLowerLimit");
		}

		{
			Collection<TrainingSession> trainingSessions;

			trainingSessions = this.repository.findTrainingSessionsByTrainingModuleId(object.getId());

			super.state(!trainingSessions.isEmpty(), "*", "developer.training-module.form.error.minimumOneTrainingSession");

			boolean canBePublished = true;
			for (TrainingSession trainingSession : trainingSessions)
				if (trainingSession.isDraftMode() == true)
					canBePublished = false;
			super.state(canBePublished, "*", "developer.training-module.form.error.cannotPublishWithDraftTrainingSession");

		}

	}

	@Override
	public void perform(final TrainingModule object) {
		assert object != null;

		object.setDraftMode(false);
		this.repository.save(object);
	}

	@Override
	public void unbind(final TrainingModule object) {
		assert object != null;

		SelectChoices choices;
		SelectChoices choices2;
		Collection<Project> projects;
		Dataset dataset;

		choices = SelectChoices.from(DifficultyLevel.class, object.getDifficultyLevel());
		projects = this.repository.findAllProjects();
		choices2 = SelectChoices.from(projects, "code", object.getProject());

		dataset = super.unbind(object, "code", "creationMoment", "details", "updateMoment", "link", "draftMode", "totalTime", "project");
		dataset.put("difficultyLevels", choices);
		dataset.put("project", choices2.getSelected().getKey());
		dataset.put("projects", choices2);
		super.getResponse().addData(dataset);
	}

}
