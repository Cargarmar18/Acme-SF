
package acme.forms;

import acme.client.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SponsorDashboard extends AbstractForm {

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	int							numberOfInvoicesTaxLessOrEqual21;
	int							numberOfSponsorshipsWithLink;

	Double						averageAmountSponsorships;
	Double						deviationAmountSponsorships;
	Integer						maximumAmountSponsorships;
	Integer						minimumAmountSponsorships;

	Double						averageQuantityOfInvoices;
	Double						deviationQuantityOfInvoices;
	Integer						maximumQuantityOfInvoices;
	Integer						minimumQuantityOfInvoices;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

}
