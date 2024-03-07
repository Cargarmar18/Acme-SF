
package acme.forms;

import acme.client.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ManagerDashboard extends AbstractForm {

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	Integer						numberOfMustUserStories;
	Integer						numberOfShouldUserStories;
	Integer						numberOfCouldUserStories;
	Integer						numberOfWillNotUserStories;

	Double						averageCostUserStories;
	Double						deviationCostUserStories;
	Integer						maximumCostUserStories;
	Integer						minimumCostUserStories;

	Double						averageCostProjects;
	Double						deviationCostProjects;
	Integer						maximumCostProjects;
	Integer						minimumCostProjects;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

}
