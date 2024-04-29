
package acme.features.developer.developerDashboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.forms.DeveloperDashboard;
import acme.roles.Developer;

@Service
public class DeveloperDeveloperDashboardShowService extends AbstractService<Developer, DeveloperDashboard> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private DeveloperDeveloperDashboardRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		int developerId;
		developerId = super.getRequest().getPrincipal().getActiveRoleId();

		Developer developer;
		developer = this.repository.findDeveloperById(developerId);

		boolean status;
		status = developer != null && super.getRequest().getPrincipal().hasRole(Developer.class);
		super.getResponse().setAuthorised(status);

	}

	@Override
	public void load() {
		int developerId;

		developerId = super.getRequest().getPrincipal().getActiveRoleId();

		DeveloperDashboard dashboard;
		int numberWithUpdateMoment;
		int numberWithLink;
		Double averageTotalTime;
		Double deviationTotalTime;
		Integer minTotalTime;
		Integer maxTotalTime;

		numberWithUpdateMoment = this.repository.findNumberWithUpdateMoment(developerId);
		numberWithLink = this.repository.findNumberWithLink(developerId);
		averageTotalTime = this.repository.averageTotalTime(developerId);
		deviationTotalTime = this.repository.deviationTotalTime(developerId);
		minTotalTime = this.repository.minTotalTime(developerId);
		maxTotalTime = this.repository.maxTotalTime(developerId);

		dashboard = new DeveloperDashboard();
		dashboard.setNumberWithUpdateMoment(numberWithUpdateMoment);
		dashboard.setNumberWithLink(numberWithLink);
		dashboard.setAverageTotalTime(averageTotalTime);
		dashboard.setDeviationTotalTime(deviationTotalTime);
		dashboard.setMinTotalTime(minTotalTime);
		dashboard.setMaxTotalTime(maxTotalTime);

		super.getBuffer().addData(dashboard);
	}

	@Override
	public void unbind(final DeveloperDashboard object) {
		Dataset dataset;
		dataset = super.unbind(object, //
			"numberWithUpdateMoment", "numberWithLink", // 
			"averageTotalTime", "deviationTotalTime", //
			"minTotalTime", "maxTotalTime");
		super.getResponse().addData(dataset);
	}

}
