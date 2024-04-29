
package acme.features.developer.trainingModule;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.project.Project;
import acme.entities.trainingModules.TrainingModule;
import acme.entities.trainingModules.TrainingSession;
import acme.roles.Developer;

@Repository
public interface DeveloperTrainingModuleRepository extends AbstractRepository {

	@Query("select t from TrainingModule t where t.developer.id = :developerId")
	Collection<TrainingModule> findManyTrainingModulesByDeveloper(int developerId);

	@Query("select t from TrainingModule t where t.id = :id")
	TrainingModule findOneTrainingModuleById(int id);

	@Query("select d from Developer d where d.id = :id")
	Developer findDeveloperById(int id);

	@Query("select p from Project p where p.draftMode = false")
	Collection<Project> findAllProjects();

	@Query("select t from TrainingSession t where t.trainingModule.id = :id")
	Collection<TrainingSession> findTrainingSessionsByTrainingModuleId(int id);

	@Query("select t from TrainingModule t where t.code = :code")
	TrainingModule findOneTrainingModuleByCode(String code);
}
