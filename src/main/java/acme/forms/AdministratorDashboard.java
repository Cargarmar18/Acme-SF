
package acme.forms;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdministratorDashboard {

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	Map<String, Integer>		numberPrincipalsEachRole;

	Double						noticesWithEmailAndLinkRatio;

	Double						nonCriticalObjectivesRatio;

	Double						averageValueRisks;
	Double						minimumValueRisks;
	Double						maximumValueRisks;
	Double						deviationValueRisks;

	Double						averageNumberClaimsLastTenWeeks;
	Double						minimumNumberClaimsLastTenWeeks;
	Double						maximumNumberClaimsLastTenWeeks;
	Double						deviationNumberClaimsLastTenWeeks;
	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

}
