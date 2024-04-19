/*
 * EmployerDutyRepository.java
 *
 * Copyright (C) 2012-2024 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.manager.userStory;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.project.Project;
import acme.entities.project.UserStory;
import acme.entities.project.UserStoryProject;
import acme.roles.Manager;

@Repository
public interface ManagerUserStoryRepository extends AbstractRepository {

	@Query("select p from Project p where p.id = :id")
	Project findOneProjectById(int id);

	@Query("select u.userStory from UserStoryProject u where u.project.id = :masterId")
	Collection<UserStory> findManyUserStoriesByMasterId(int masterId);

	@Query("select u from UserStory u where u.manager.id = :managerId")
	Collection<UserStory> findManyUserStoriesByManagerId(int managerId);

	@Query("select u from UserStory u where u.id = :id")
	UserStory findUserStoryById(int id);

	@Query("select m from Manager m where m.id = :id")
	Manager findManagerById(int id);

	@Query("select u from UserStoryProject u where u.userStory.id = :id")
	Collection<UserStoryProject> findManyUserStoryProjectsByUserStoryId(int id);

}
