
package acme.forms;

import java.util.Map;

import acme.client.data.AbstractForm;
import acme.entities.project.PriorityStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ManagerDashboard extends AbstractForm {

	// Serialisation identifier -----------------------------------------------

	private static final long		serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	Map<PriorityStatus, Integer>	numberOfPrioritiesUserStories;

	Double							averageCostUserStories;
	Double							deviationCostUserStories;
	Integer							maximumCostUserStories;
	Integer							minimumCostUserStories;

	Double							averageCostProjects;
	Double							deviationCostProjects;
	Integer							maximumCostProjects;
	Integer							minimumCostProjects;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

}
