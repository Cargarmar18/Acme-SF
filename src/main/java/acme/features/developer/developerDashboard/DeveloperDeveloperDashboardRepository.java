
package acme.features.developer.developerDashboard;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.roles.Developer;

@Repository
public interface DeveloperDeveloperDashboardRepository extends AbstractRepository {

	@Query("select d from Developer d where d.id = :id")
	Developer findDeveloperById(int id);

	@Query("select count(t) from TrainingModule t where t.updateMoment is not null and t.developer.id = :id")
	int findNumberWithUpdateMoment(int id);

	@Query("select count(t) from TrainingSession t where t.link not like '' and t.link is not null and t.trainingModule.developer.id = :id")
	int findNumberWithLink(int id);

	@Query("select avg(t.totalTime) from TrainingModule t where t.developer.id = :developerId")
	Double averageTotalTime(int developerId);

	@Query("select sqrt(avg(t.totalTime * t.totalTime) - avg(t.totalTime) * avg(t.totalTime)) from TrainingModule t where t.developer.id = :developerId")
	Double deviationTotalTime(int developerId);

	@Query("select min(t.totalTime) from TrainingModule t where t.developer.id = :developerId")
	Integer minTotalTime(int developerId);

	@Query("select max(t.totalTime) from TrainingModule t where t.developer.id = :developerId")
	Integer maxTotalTime(int developerId);

}
