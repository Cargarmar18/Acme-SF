
package acme.features.developer.trainingSession;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.project.Project;
import acme.entities.trainingModules.TrainingModule;
import acme.entities.trainingModules.TrainingSession;
import acme.roles.Developer;

@Repository
public interface DeveloperTrainingSessionRepository extends AbstractRepository {

	@Query("select t from TrainingSession t where t.trainingModule.developer.id = :developerId")
	Collection<TrainingSession> findManyTrainingSessionsByDeveloper(int developerId);

	@Query("select t from TrainingSession t where t.id = :id")
	TrainingSession findOneTrainingSessionById(int id);

	@Query("select t from TrainingSession t where t.trainingModule.id = :trainingModuleId")
	Collection<TrainingSession> findManyTrainingSessionsByTrainingModuleId(int trainingModuleId);

	@Query("select d from Developer d where d.id = :id")
	Developer findDeveloperById(int id);

	@Query("select p from Project p where p.draftMode = false")
	Collection<Project> findAllProjects();

	@Query("select t from TrainingModule t where t.developer.id = :developerId")
	Collection<TrainingModule> findManyTrainingModulesByDeveloperId(int developerId);

	@Query("select t from TrainingModule t where t.developer.id = :developerId and t.draftMode = true")
	Collection<TrainingModule> findManyTrainingModulesDraftModeByDeveloperId(int developerId);

	@Query("select t from TrainingModule t where t.id = :id")
	TrainingModule findOneTrainingModuleById(int id);

	@Query("select t from TrainingSession t where t.code = :code")
	TrainingSession findOneTrainingSessionByCode(String code);

}
