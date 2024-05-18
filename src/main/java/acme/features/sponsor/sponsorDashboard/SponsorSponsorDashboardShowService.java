
package acme.features.sponsor.sponsorDashboard;

import java.util.ArrayList;
import java.util.Currency;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.forms.SponsorDashboard;
import acme.roles.Sponsor;

@Service
public class SponsorSponsorDashboardShowService extends AbstractService<Sponsor, SponsorDashboard> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private SponsorSponsorDashboardRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		int sponsorId;

		sponsorId = super.getRequest().getPrincipal().getActiveRoleId();

		String currenciesString = this.repository.findConfiguration().getAcceptedCurrencies();

		List<String> currencies = new ArrayList<>();
		int startIndex = 0;
		int nextIndex;
		while ((nextIndex = currenciesString.indexOf(';', startIndex)) != -1) {
			String currencyCode = currenciesString.substring(startIndex, nextIndex);
			currencies.add(currencyCode);
			startIndex = nextIndex + 1;
		}
		String lastCurrencyCode = currenciesString.substring(startIndex);
		currencies.add(lastCurrencyCode);

		Map<Currency, Double> averageAmountSponsorships = new HashMap<>();
		for (String currencyCode : currencies) {
			Double averageAmount = this.repository.averageAmountSponsorshipsByCurrency(currencyCode, sponsorId);
			Currency currency = Currency.getInstance(currencyCode);
			averageAmountSponsorships.put(currency, averageAmount);
		}

		Map<Currency, Double> deviationAmountSponsorships = new HashMap<>();
		for (String currencyCode : currencies) {
			Double deviationAmount = this.repository.deviationAmountSponsorships(currencyCode, sponsorId);
			Currency currency = Currency.getInstance(currencyCode);
			deviationAmountSponsorships.put(currency, deviationAmount);
		}

		Map<Currency, Double> maximumAmountSponsorships = new HashMap<>();
		for (String currencyCode : currencies) {
			Double maximumAmount = this.repository.maximumAmountSponsorships(currencyCode, sponsorId);
			Currency currency = Currency.getInstance(currencyCode);
			maximumAmountSponsorships.put(currency, maximumAmount);
		}

		Map<Currency, Double> minimumAmountSponsorships = new HashMap<>();
		for (String currencyCode : currencies) {
			Double minimumAmount = this.repository.minimumAmountSponsorships(currencyCode, sponsorId);
			Currency currency = Currency.getInstance(currencyCode);
			minimumAmountSponsorships.put(currency, minimumAmount);
		}

		Map<Currency, Double> averageQuantityOfInvoices = new HashMap<>();
		for (String currencyCode : currencies) {
			Double averageQuantity = this.repository.averageQuantityOfInvoices(currencyCode, sponsorId);
			Currency currency = Currency.getInstance(currencyCode);
			averageQuantityOfInvoices.put(currency, averageQuantity);
		}

		Map<Currency, Double> deviationQuantityOfInvoices = new HashMap<>();
		for (String currencyCode : currencies) {
			Double deviationQuantity = this.repository.deviationQuantityOfInvoices(currencyCode, sponsorId);
			Currency currency = Currency.getInstance(currencyCode);
			deviationQuantityOfInvoices.put(currency, deviationQuantity);
		}

		Map<Currency, Double> maximumQuantityOfInvoices = new HashMap<>();
		for (String currencyCode : currencies) {
			Double maximumQuantity = this.repository.maximumQuantityOfInvoices(currencyCode, sponsorId);
			Currency currency = Currency.getInstance(currencyCode);
			maximumQuantityOfInvoices.put(currency, maximumQuantity);
		}

		Map<Currency, Double> minimumQuantityOfInvoices = new HashMap<>();
		for (String currencyCode : currencies) {
			Double minimumQuantity = this.repository.minimumQuantityOfInvoices(currencyCode, sponsorId);
			Currency currency = Currency.getInstance(currencyCode);
			minimumQuantityOfInvoices.put(currency, minimumQuantity);
		}

		SponsorDashboard dashboard;
		int numberOfInvoicesTaxLessOrEqual21;
		int numberOfSponsorshipsWithLink;

		numberOfInvoicesTaxLessOrEqual21 = this.repository.findNumberOfInvoicesTaxLessOrEqual21(sponsorId);
		numberOfSponsorshipsWithLink = this.repository.findNumberOfSponsorshipsWithLink(sponsorId);

		dashboard = new SponsorDashboard();
		dashboard.setNumberOfInvoicesTaxLessOrEqual21(numberOfInvoicesTaxLessOrEqual21);
		dashboard.setNumberOfSponsorshipsWithLink(numberOfSponsorshipsWithLink);
		dashboard.setAverageAmountSponsorships(averageAmountSponsorships);
		dashboard.setDeviationAmountSponsorships(deviationAmountSponsorships);
		dashboard.setMaximumAmountSponsorships(maximumAmountSponsorships);
		dashboard.setMinimumAmountSponsorships(minimumAmountSponsorships);
		dashboard.setAverageQuantityOfInvoices(averageQuantityOfInvoices);
		dashboard.setDeviationQuantityOfInvoices(deviationQuantityOfInvoices);
		dashboard.setMaximumQuantityOfInvoices(maximumQuantityOfInvoices);
		dashboard.setMinimumQuantityOfInvoices(minimumQuantityOfInvoices);

		super.getBuffer().addData(dashboard);
	}

	@Override
	public void unbind(final SponsorDashboard object) {
		Dataset dataset;
		Double averageAmountUSD;
		Double averageAmountEUR;
		Double averageAmountGBP;
		Double averageAmountTHB;
		Double averageAmountCAD;

		Double deviationAmountUSD;
		Double deviationAmountEUR;
		Double deviationAmountGBP;
		Double deviationAmountTHB;
		Double deviationAmountCAD;

		Double maximumAmountUSD;
		Double maximumAmountEUR;
		Double maximumAmountGBP;
		Double maximumAmountTHB;
		Double maximumAmountCAD;

		Double minimumAmountUSD;
		Double minimumAmountEUR;
		Double minimumAmountGBP;
		Double minimumAmountTHB;
		Double minimumAmountCAD;

		Double averageQuantityUSD;
		Double averageQuantityEUR;
		Double averageQuantityGBP;
		Double averageQuantityTHB;
		Double averageQuantityCAD;

		Double deviationQuantityUSD;
		Double deviationQuantityEUR;
		Double deviationQuantityGBP;
		Double deviationQuantityTHB;
		Double deviationQuantityCAD;

		Double maximumQuantityUSD;
		Double maximumQuantityEUR;
		Double maximumQuantityGBP;
		Double maximumQuantityTHB;
		Double maximumQuantityCAD;

		Double minimumQuantityUSD;
		Double minimumQuantityEUR;
		Double minimumQuantityGBP;
		Double minimumQuantityTHB;
		Double minimumQuantityCAD;

		averageAmountUSD = object.getAverageAmountSponsorships().get(Currency.getInstance("USD"));
		averageAmountEUR = object.getAverageAmountSponsorships().get(Currency.getInstance("EUR"));
		averageAmountGBP = object.getAverageAmountSponsorships().get(Currency.getInstance("GBP"));
		averageAmountTHB = object.getAverageAmountSponsorships().get(Currency.getInstance("THB"));
		averageAmountCAD = object.getAverageAmountSponsorships().get(Currency.getInstance("CAD"));

		deviationAmountUSD = object.getDeviationAmountSponsorships().get(Currency.getInstance("USD"));
		deviationAmountEUR = object.getDeviationAmountSponsorships().get(Currency.getInstance("EUR"));
		deviationAmountGBP = object.getDeviationAmountSponsorships().get(Currency.getInstance("GBP"));
		deviationAmountTHB = object.getDeviationAmountSponsorships().get(Currency.getInstance("THB"));
		deviationAmountCAD = object.getDeviationAmountSponsorships().get(Currency.getInstance("CAD"));

		maximumAmountUSD = object.getDeviationAmountSponsorships().get(Currency.getInstance("USD"));
		maximumAmountEUR = object.getDeviationAmountSponsorships().get(Currency.getInstance("EUR"));
		maximumAmountGBP = object.getDeviationAmountSponsorships().get(Currency.getInstance("GBP"));
		maximumAmountTHB = object.getDeviationAmountSponsorships().get(Currency.getInstance("THB"));
		maximumAmountCAD = object.getDeviationAmountSponsorships().get(Currency.getInstance("CAD"));

		minimumAmountUSD = object.getMinimumAmountSponsorships().get(Currency.getInstance("USD"));
		minimumAmountEUR = object.getMinimumAmountSponsorships().get(Currency.getInstance("EUR"));
		minimumAmountGBP = object.getMinimumAmountSponsorships().get(Currency.getInstance("GBP"));
		minimumAmountTHB = object.getMinimumAmountSponsorships().get(Currency.getInstance("THB"));
		minimumAmountCAD = object.getMinimumAmountSponsorships().get(Currency.getInstance("CAD"));

		averageQuantityUSD = object.getAverageQuantityOfInvoices().get(Currency.getInstance("USD"));
		averageQuantityEUR = object.getAverageQuantityOfInvoices().get(Currency.getInstance("EUR"));
		averageQuantityGBP = object.getAverageQuantityOfInvoices().get(Currency.getInstance("GBP"));
		averageQuantityTHB = object.getAverageQuantityOfInvoices().get(Currency.getInstance("THB"));
		averageQuantityCAD = object.getAverageQuantityOfInvoices().get(Currency.getInstance("CAD"));

		deviationQuantityUSD = object.getDeviationQuantityOfInvoices().get(Currency.getInstance("USD"));
		deviationQuantityEUR = object.getDeviationQuantityOfInvoices().get(Currency.getInstance("EUR"));
		deviationQuantityGBP = object.getDeviationQuantityOfInvoices().get(Currency.getInstance("GBP"));
		deviationQuantityTHB = object.getDeviationQuantityOfInvoices().get(Currency.getInstance("THB"));
		deviationQuantityCAD = object.getDeviationQuantityOfInvoices().get(Currency.getInstance("CAD"));

		maximumQuantityUSD = object.getMaximumQuantityOfInvoices().get(Currency.getInstance("USD"));
		maximumQuantityEUR = object.getMaximumQuantityOfInvoices().get(Currency.getInstance("EUR"));
		maximumQuantityGBP = object.getMaximumQuantityOfInvoices().get(Currency.getInstance("GBP"));
		maximumQuantityTHB = object.getMaximumQuantityOfInvoices().get(Currency.getInstance("THB"));
		maximumQuantityCAD = object.getMaximumQuantityOfInvoices().get(Currency.getInstance("CAD"));

		minimumQuantityUSD = object.getMinimumQuantityOfInvoices().get(Currency.getInstance("USD"));
		minimumQuantityEUR = object.getMinimumQuantityOfInvoices().get(Currency.getInstance("EUR"));
		minimumQuantityGBP = object.getMinimumQuantityOfInvoices().get(Currency.getInstance("GBP"));
		minimumQuantityTHB = object.getMinimumQuantityOfInvoices().get(Currency.getInstance("THB"));
		minimumQuantityCAD = object.getMinimumQuantityOfInvoices().get(Currency.getInstance("CAD"));

		dataset = super.unbind(object, //
			"numberOfInvoicesTaxLessOrEqual21", "numberOfSponsorshipsWithLink");

		dataset.put("averageAmountUSD", averageAmountUSD);
		dataset.put("averageAmountEUR", averageAmountEUR);
		dataset.put("averageAmountGBP", averageAmountGBP);
		dataset.put("averageAmountTHB", averageAmountTHB);
		dataset.put("averageAmountCAD", averageAmountCAD);

		dataset.put("deviationAmountUSD", deviationAmountUSD);
		dataset.put("deviationAmountEUR", deviationAmountEUR);
		dataset.put("deviationAmountGBP", deviationAmountGBP);
		dataset.put("deviationAmountTHB", deviationAmountTHB);
		dataset.put("deviationAmountCAD", deviationAmountCAD);

		dataset.put("maximumAmountUSD", maximumAmountUSD);
		dataset.put("maximumAmountEUR", maximumAmountEUR);
		dataset.put("maximumAmountGBP", maximumAmountGBP);
		dataset.put("maximumAmountTHB", maximumAmountTHB);
		dataset.put("maximumAmountCAD", maximumAmountCAD);

		dataset.put("minimumAmountUSD", minimumAmountUSD);
		dataset.put("minimumAmountEUR", minimumAmountEUR);
		dataset.put("minimumAmountGBP", minimumAmountGBP);
		dataset.put("minimumAmountTHB", minimumAmountTHB);
		dataset.put("minimumAmountCAD", minimumAmountCAD);

		dataset.put("averageQuantityUSD", averageQuantityUSD);
		dataset.put("averageQuantityEUR", averageQuantityEUR);
		dataset.put("averageQuantityGBP", averageQuantityGBP);
		dataset.put("averageQuantityTHB", averageQuantityTHB);
		dataset.put("averageQuantityCAD", averageQuantityCAD);

		dataset.put("deviationQuantityUSD", deviationQuantityUSD);
		dataset.put("deviationQuantityEUR", deviationQuantityEUR);
		dataset.put("deviationQuantityGBP", deviationQuantityGBP);
		dataset.put("deviationQuantityTHB", deviationQuantityTHB);
		dataset.put("deviationQuantityCAD", deviationQuantityCAD);

		dataset.put("maximumQuantityUSD", maximumQuantityUSD);
		dataset.put("maximumQuantityEUR", maximumQuantityEUR);
		dataset.put("maximumQuantityGBP", maximumQuantityGBP);
		dataset.put("maximumQuantityTHB", maximumQuantityTHB);
		dataset.put("maximumQuantityCAD", maximumQuantityCAD);

		dataset.put("minimumQuantityUSD", minimumQuantityUSD);
		dataset.put("minimumQuantityEUR", minimumQuantityEUR);
		dataset.put("minimumQuantityGBP", minimumQuantityGBP);
		dataset.put("minimumQuantityTHB", minimumQuantityTHB);
		dataset.put("minimumQuantityCAD", minimumQuantityCAD);

		super.getResponse().addData(dataset);

	}

}
