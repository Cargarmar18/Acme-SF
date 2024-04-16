/*
 * EmployerJobController.java
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

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.entities.project.UserStory;
import acme.roles.Manager;

@Controller
public class ManagerUserStoryController extends AbstractController<Manager, UserStory> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ManagerUserStoryListService		listService;

	@Autowired
	private ManagerUserStoryListMineService	listMineService;

	@Autowired
	private ManagerUserStoryShowService		showService;

	@Autowired
	private ManagerUserStoryPublishService	publishService;
	
	@Autowired
	private ManagerUserStoryCreateService	createService;

	// Constructors -----------------------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("create", this.createService);
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("list", this.listService);
		super.addCustomCommand("list-mine", "list", this.listMineService);
		super.addCustomCommand("publish", "update", this.publishService);
	}

}
