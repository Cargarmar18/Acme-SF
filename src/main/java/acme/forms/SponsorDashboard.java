
package acme.forms;

import java.util.Currency;
import java.util.Map;

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

	Map<Currency, Double>		averageAmountSponsorships;
	Map<Currency, Double>		deviationAmountSponsorships;
	Map<Currency, Double>		maximumAmountSponsorships;
	Map<Currency, Double>		minimumAmountSponsorships;

	Map<Currency, Double>		averageQuantityOfInvoices;
	Map<Currency, Double>		deviationQuantityOfInvoices;
	Map<Currency, Double>		maximumQuantityOfInvoices;
	Map<Currency, Double>		minimumQuantityOfInvoices;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

}
