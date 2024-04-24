
package acme.features.developer.trainingModule;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.trainingModules.TrainingModule;

@Repository
public interface DeveloperTrainingModuleRepository extends AbstractRepository {

	@Query("select t from training-module t")
	Collection<TrainingModule> findAllTrainingModules();

	/*
	 * @Query("select j from Job j where j.id = :id")
	 * TrainingModule findOneJobById(int id);
	 * 
	 * @Query("select j from Job j where j.draftMode = false and j.deadline > :currentMoment")
	 * Collection<TrainingModule> findManyJobsByAvailability(Date currentMoment);
	 * 
	 * @Query("select c from Company c")
	 * Collection<TrainingModule> findAllContractors();
	 */
}
