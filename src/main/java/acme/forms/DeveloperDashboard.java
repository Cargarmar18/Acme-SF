
package acme.forms;

import acme.client.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeveloperDashboard extends AbstractForm {

	// Serialisation identifier -----------------------------------------------
	private static final long	serialVersionUID	= 1L;
	// Query attributes -------------------------------------------------------------

	int				numberWithUpdateMoment;

	int				numberWithLink;

	Double				averageTotalTime;

	Double				deviationTotalTime;

	Integer				minTotalTime;

	Integer				maxTotalTime;

}
