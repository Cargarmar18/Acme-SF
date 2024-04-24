/*
 * AdministratorDashboardRepository.java
 *
 * Copyright (C) 2012-2024 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.manager.managerDashboard;

import java.util.EnumMap;
import java.util.Map;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.project.PriorityStatus;

@Repository
public interface ManagerManagerDashboardRepository extends AbstractRepository {

	@Query("select count(*) from UserStory u where u.priority = :priority and u.manager.id = :managerId")
	Integer countPriorityInUserStories(PriorityStatus priority, int managerId);

	default Map<PriorityStatus, Integer> numberOfPriorityStatusUserStories(final int managerId) {
		Map<PriorityStatus, Integer> counter = new EnumMap<>(PriorityStatus.class);
		for (PriorityStatus priority : PriorityStatus.values()) {
			Integer values = this.countPriorityInUserStories(priority, managerId);
			counter.put(priority, values);
		}
		return counter;
	}

	@Query("select avg(u.cost) from UserStory u where u.manager.id = :managerId")
	Double averageCostUserStories(int managerId);

	@Query("select sqrt(avg(u.cost * u.cost) - avg(u.cost) * avg(u.cost)) from UserStory u where u.manager.id = :managerId")
	Double deviationCostUserStories(int managerId);

	@Query("select max(u.cost) from UserStory u where u.manager.id = :managerId")
	Integer maximumCostUserStories(int managerId);

	@Query("select min(u.cost) from UserStory u where u.manager.id = :managerId")
	Integer minimumCostUserStories(int managerId);

	@Query("select avg(p.cost) from Project p where p.manager.id = :managerId")
	Double averageCostProjects(int managerId);

	@Query("select sqrt(avg(p.cost * p.cost) - avg(p.cost) * avg(p.cost)) from Project p where p.manager.id = :managerId")
	Double deviationCostProjects(int managerId);

	@Query("select max(p.cost) from Project p where p.manager.id = :managerId")
	Integer maximumCostProjects(int managerId);

	@Query("select min(p.cost) from Project p where p.manager.id = :managerId")
	Integer minimumCostProjects(int managerId);

}
