
package acme.forms;

import acme.client.data.AbstractForm;

public class DeveloperDashboard extends AbstractForm {

	// Serialisation identifier -----------------------------------------------
	private static final long	serialVersionUID	= 1L;

	// Query attributes -------------------------------------------------------------

	private int					numberWithUpdateMoment;

	private int					numberWithLink;

	private Double				averageTotalTime;

	private Double				deviationTotalTime;

	private Integer				minTotalTime;

	private Integer				maxTotalTime;

}
