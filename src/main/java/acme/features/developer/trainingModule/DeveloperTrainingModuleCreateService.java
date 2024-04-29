
package acme.features.developer.trainingModule;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.project.Project;
import acme.entities.trainingModules.DifficultyLevel;
import acme.entities.trainingModules.TrainingModule;
import acme.roles.Developer;

@Service
public class DeveloperTrainingModuleCreateService extends AbstractService<Developer, TrainingModule> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private DeveloperTrainingModuleRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		TrainingModule object;
		Developer developer;
		int id;
		Date moment = MomentHelper.getCurrentMoment();

		id = super.getRequest().getPrincipal().getActiveRoleId();
		developer = this.repository.findDeveloperById(id);
		object = new TrainingModule();
		object.setDeveloper(developer);
		object.setCreationMoment(moment);
		object.setDraftMode(true);
		object.setUpdateMoment(null);

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

			codeValid = this.repository.findOneTrainingModuleByCode(object.getCode());
			super.state(codeValid == null, "code", "developer.training-module.form.error.duplicated");
		}

		if (!super.getBuffer().getErrors().hasErrors("creationMoment"))
			super.state(MomentHelper.isAfter(object.getCreationMoment(), lowerLimit), "creationMoment", "developer.training-module.form.error.creationMomentBeforeLowerLimit");
	}

	@Override
	public void perform(final TrainingModule object) {
		assert object != null;

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

		dataset = super.unbind(object, "code", "creationMoment", "details", "updateMoment", "link", "totalTime", "project");
		dataset.put("difficultyLevels", choices);
		dataset.put("project", choices2.getSelected().getKey());
		dataset.put("projects", choices2);

		super.getResponse().addData(dataset);

	}

}
