/*
 * EmployerWorksForRepository.java
 *
 * Copyright (C) 2012-2024 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.manager.userStoryProject;

import java.util.Collection;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.project.Project;
import acme.entities.project.UserStory;
import acme.entities.project.UserStoryProject;

@Repository
public interface ManagerUserStoryProjectRepository extends AbstractRepository {

	@Query("select p from Project p where p.id = :id")
	Project findOneProjectById(int id);

	@Query("select u from UserStory u where u.id = :id")
	UserStory findOneUserStoryById(int id);

	@Query("select up from UserStoryProject up where up.project.id = :id")
	UserStoryProject findOneUserStoryProjectByProjectId(int id);

	@Query("select u from UserStory u where u.id not in (select up.userStory.id from UserStoryProject up where up.project.id = :projectId) and u.manager.id = :managerId")
	Collection<UserStory> findManyAvailableUserStoriesByManagerId(int managerId, int projectId);

	@Query("select up.userStory from UserStoryProject up where up.project.id = :projectId and up.userStory.manager.id = :managerId")
	Collection<UserStory> findManyAvailableUserStoriesFromProjectByManagerId(int managerId, int projectId);

	@Modifying
	@Query("delete from UserStoryProject up where up.project.id = :projectId and up.userStory.id = :userStoryId")
	void deleteUserStoryProject(int projectId, int userStoryId);

}
