
package acme.features.developer.trainingSession;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.trainingModules.TrainingSession;
import acme.roles.Developer;

@Service
public class DeveloperTrainingSessionListMineService extends AbstractService<Developer, TrainingSession> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private DeveloperTrainingSessionRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		int developerId;
		developerId = super.getRequest().getPrincipal().getActiveRoleId();

		Developer developer;
		developer = this.repository.findDeveloperById(developerId);

		boolean status;
		status = developer != null && super.getRequest().getPrincipal().hasRole(Developer.class);
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Collection<TrainingSession> objects;
		int developerId;

		developerId = super.getRequest().getPrincipal().getActiveRoleId();
		objects = this.repository.findManyTrainingSessionsByDeveloper(developerId);

		super.getBuffer().addData(objects);
	}
	@Override
	public void unbind(final TrainingSession object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "code", "location", "instructor", "draftMode");

		super.getResponse().addData(dataset);
	}
}
