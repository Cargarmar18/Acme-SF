/*
 * package acme.features.sponsor.sponsorDashboard;
 * 
 * import org.springframework.data.jpa.repository.Query;
 * import org.springframework.stereotype.Repository;
 * 
 * import acme.client.repositories.AbstractRepository;
 * 
 * @Repository
 * public interface SponsorSponsorDashboardRepository extends AbstractRepository {
 * 
 * @Query("select count(i) from Invoice i where i.tax <= 21.00 and i.sponsorship.sponsor.id = :sponsorId")
 * int findNumberOfInvoicesTaxLessOrEqual21(int sponsorId);
 * 
 * @Query("select count(s) from Sponsorship s where s.link is not null and s.id = :sponsorId")
 * int findNumberOfSponsorshipsWithLink(int sponsorId);
 * /*
 * 
 * @Query("select avg(s.amount.amount) from Sponsorship s u where s.amouunt.currency = :currency and s.id = :sponsorId")
 * Double averageAmountSponsorships(Currency currency, int sponsorId);
 * 
 * default Map<Currency, Double> averageAmountSponsorships(final int sponsorId) {
 * 
 * Map<Currency, Double> counter = new EnumMap<>(C);
 * for (PriorityStatus priority : PriorityStatus.values()) {
 * Integer values = this.countPriorityInUserStories(priority, managerId);
 * counter.put(priority, values);
 * }
 * return counter;
 * }
 * 
 * @Query("select avg(u.cost) from UserStory u where u.manager.id = :managerId")
 * Double averageCostUserStories(int managerId);
 * 
 * @Query("select sqrt(avg(u.cost * u.cost) - avg(u.cost) * avg(u.cost)) from UserStory u where u.manager.id = :managerId")
 * Double deviationCostUserStories(int managerId);
 * 
 * @Query("select max(u.cost) from UserStory u where u.manager.id = :managerId")
 * Integer maximumCostUserStories(int managerId);
 * 
 * @Query("select min(u.cost) from UserStory u where u.manager.id = :managerId")
 * Integer minimumCostUserStories(int managerId);
 * 
 * @Query("select avg(p.cost) from Project p where p.manager.id = :managerId")
 * Double averageCostProjects(int managerId);
 * 
 * @Query("select sqrt(avg(p.cost * p.cost) - avg(p.cost) * avg(p.cost)) from Project p where p.manager.id = :managerId")
 * Double deviationCostProjects(int managerId);
 * 
 * @Query("select max(p.cost) from Project p where p.manager.id = :managerId")
 * Integer maximumCostProjects(int managerId);
 * 
 * @Query("select min(p.cost) from Project p where p.manager.id = :managerId")
 * Integer minimumCostProjects(int managerId);
 * 
 * }
 */
