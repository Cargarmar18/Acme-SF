/*
 * AdministratorDashboardShowService.java
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

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.project.PriorityStatus;
import acme.forms.ManagerDashboard;
import acme.roles.Manager;

@Service
public class ManagerManagerDashboardShowService extends AbstractService<Manager, ManagerDashboard> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ManagerManagerDashboardRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		int managerId;

		managerId = super.getRequest().getPrincipal().getActiveRoleId();

		ManagerDashboard dashboard;
		Map<PriorityStatus, Integer> numberOfPriorityStatusUserStories;
		Double averageCostUserStories;
		Double deviationCostUserStories;
		Integer maximumCostUserStories;
		Integer minimumCostUserStories;
		Double averageCostProjects;
		Double deviationCostProjects;
		Integer maximumCostProjects;
		Integer minimumCostProjects;

		numberOfPriorityStatusUserStories = this.repository.numberOfPriorityStatusUserStories(managerId);
		averageCostUserStories = this.repository.averageCostUserStories(managerId);
		deviationCostUserStories = this.repository.deviationCostUserStories(managerId);
		maximumCostUserStories = this.repository.maximumCostUserStories(managerId);
		minimumCostUserStories = this.repository.minimumCostUserStories(managerId);
		averageCostProjects = this.repository.averageCostProjects(managerId);
		deviationCostProjects = this.repository.deviationCostProjects(managerId);
		maximumCostProjects = this.repository.maximumCostProjects(managerId);
		minimumCostProjects = this.repository.minimumCostProjects(managerId);

		dashboard = new ManagerDashboard();
		dashboard.setNumberOfPrioritiesUserStories(numberOfPriorityStatusUserStories);
		dashboard.setAverageCostUserStories(averageCostUserStories);
		dashboard.setDeviationCostUserStories(deviationCostUserStories);
		dashboard.setMaximumCostUserStories(maximumCostUserStories);
		dashboard.setMinimumCostUserStories(minimumCostUserStories);
		dashboard.setAverageCostProjects(averageCostProjects);
		dashboard.setDeviationCostProjects(deviationCostProjects);
		dashboard.setMaximumCostProjects(maximumCostProjects);
		dashboard.setMinimumCostProjects(minimumCostProjects);

		super.getBuffer().addData(dashboard);
	}

	@Override
	public void unbind(final ManagerDashboard object) {
		Dataset dataset;
		Integer numberOfMustStatusUserStories;
		Integer numberOfShouldStatusUserStories;
		Integer numberOfCouldStatusUserStories;
		Integer numberOfWillNotStatusUserStories;

		numberOfMustStatusUserStories = object.getNumberOfPrioritiesUserStories().get(PriorityStatus.MUST);
		numberOfShouldStatusUserStories = object.getNumberOfPrioritiesUserStories().get(PriorityStatus.SHOULD);
		numberOfCouldStatusUserStories = object.getNumberOfPrioritiesUserStories().get(PriorityStatus.COULD);
		numberOfWillNotStatusUserStories = object.getNumberOfPrioritiesUserStories().get(PriorityStatus.WILLNOT);

		dataset = super.unbind(object, //
			"averageCostUserStories", "deviationCostUserStories", // 
			"maximumCostUserStories", "minimumCostUserStories", //
			"averageCostProjects", "deviationCostProjects", //
			"maximumCostProjects", "minimumCostProjects");

		dataset.put("numberOfMustStatusUserStories", numberOfMustStatusUserStories);
		dataset.put("numberOfShouldStatusUserStories", numberOfShouldStatusUserStories);
		dataset.put("numberOfCouldStatusUserStories", numberOfCouldStatusUserStories);
		dataset.put("numberOfWillNotStatusUserStories", numberOfWillNotStatusUserStories);

		super.getResponse().addData(dataset);
	}

}
