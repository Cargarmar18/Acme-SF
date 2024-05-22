
package acme.features.sponsor.sponsorDashboard;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.configuration.Configuration;

@Repository
public interface SponsorSponsorDashboardRepository extends AbstractRepository {

	@Query("select count(i) from Invoice i where i.tax <= 21.00 and i.sponsorship.sponsor.id = :sponsorId")
	int findNumberOfInvoicesTaxLessOrEqual21(int sponsorId);

	@Query("select count(s) from Sponsorship s where s.moreInfo is not null and s.sponsor.id = :sponsorId")
	int findNumberOfSponsorshipsWithLink(int sponsorId);

	@Query("select c from Configuration c")
	Configuration findConfiguration();

	@Query("select avg(s.amount.amount) from Sponsorship s where s.amount.currency = :currency and s.sponsor.id = :sponsorId")
	Double averageAmountSponsorshipsByCurrency(String currency, int sponsorId);

	@Query("select sqrt(avg(s.amount.amount * s.amount.amount) - avg(s.amount.amount) * avg(s.amount.amount)) from Sponsorship s where s.amount.currency = :currency and s.sponsor.id = :sponsorId")
	Double deviationAmountSponsorships(String currency, int sponsorId);

	@Query("select max(s.amount.amount) from Sponsorship s where s.amount.currency = :currency and s.sponsor.id = :sponsorId")
	Double maximumAmountSponsorships(String currency, int sponsorId);

	@Query("select min(s.amount.amount) from Sponsorship s where s.amount.currency = :currency and s.sponsor.id = :sponsorId")
	Double minimumAmountSponsorships(String currency, int sponsorId);

	@Query("select avg(i.invoiceQuantity.amount) from Invoice i where i.invoiceQuantity.currency = :currency and i.sponsorship.sponsor.id = :sponsorId")
	Double averageQuantityOfInvoices(String currency, int sponsorId);

	@Query("select sqrt(avg(i.invoiceQuantity.amount * i.invoiceQuantity.amount) - avg(i.invoiceQuantity.amount) * avg(i.invoiceQuantity.amount)) from Invoice i where i.invoiceQuantity.currency = :currency and i.sponsorship.sponsor.id = :sponsorId")
	Double deviationQuantityOfInvoices(String currency, int sponsorId);

	@Query("select max(i.invoiceQuantity.amount) from Invoice i where i.invoiceQuantity.currency = :currency and i.sponsorship.sponsor.id = :sponsorId")
	Double maximumQuantityOfInvoices(String currency, int sponsorId);

	@Query("select min(i.invoiceQuantity.amount) from Invoice i where i.invoiceQuantity.currency = :currency and i.sponsorship.sponsor.id = :sponsorId")
	Double minimumQuantityOfInvoices(String currency, int sponsorId);

}
