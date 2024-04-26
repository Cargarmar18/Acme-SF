/*
 * ProjectUserStoryProjectCreateService.java
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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.project.Project;
import acme.entities.project.UserStory;
import acme.entities.project.UserStoryProject;
import acme.roles.Manager;

@Service
public class ManagerUserStoryProjectCreateService extends AbstractService<Manager, UserStoryProject> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ManagerUserStoryProjectRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int masterId;
		Project project;

		masterId = super.getRequest().getData("masterId", int.class);
		project = this.repository.findOneProjectById(masterId);
		status = project != null && super.getRequest().getPrincipal().hasRole(project.getManager()) && project.getManager().getId() == super.getRequest().getPrincipal().getActiveRoleId();

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		int masterId;
		Project project;
		UserStoryProject object;

		masterId = super.getRequest().getData("masterId", int.class);
		project = this.repository.findOneProjectById(masterId);

		object = new UserStoryProject();
		object.setUserStory(null);
		object.setProject(project);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final UserStoryProject object) {
		assert object != null;

		int userStoryId;
		UserStory userStory;

		userStoryId = super.getRequest().getData("userStory", int.class);
		userStory = this.repository.findOneUserStoryById(userStoryId);
		object.setUserStory(userStory);
	}

	@Override
	public void validate(final UserStoryProject object) {
		assert object != null;

		super.state(object.getProject().isDraftMode() == true, "*", "manager.user-story-project.form.error.draftMode");

	}

	@Override
	public void perform(final UserStoryProject object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final UserStoryProject object) {
		assert object != null;

		int projectId, managerId;
		Collection<UserStory> userStories;
		Project project;
		SelectChoices choices;
		Dataset dataset;

		projectId = super.getRequest().getData("masterId", int.class);
		project = this.repository.findOneProjectById(projectId);

		managerId = super.getRequest().getPrincipal().getActiveRoleId();
		userStories = this.repository.findManyAvailableUserStoriesByManagerId(managerId, projectId);
		choices = SelectChoices.from(userStories, "title", object.getUserStory());

		dataset = super.unbind(object, "project", "userStory");
		dataset.put("projectCode", project.getCode());
		dataset.put("userStory", choices.getSelected().getKey());
		dataset.put("userStories", choices);

		super.getResponse().addData(dataset);
	}

}
