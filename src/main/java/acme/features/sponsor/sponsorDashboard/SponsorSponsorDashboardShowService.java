/*
 * package acme.features.sponsor.sponsorDashboard;
 * 
 * import org.springframework.beans.factory.annotation.Autowired;
 * import org.springframework.stereotype.Service;
 * 
 * import acme.client.data.models.Dataset;
 * import acme.client.services.AbstractService;
 * import acme.forms.SponsorDashboard;
 * import acme.roles.Sponsor;
 * 
 * @Service
 * public class SponsorSponsorDashboardShowService extends AbstractService<Sponsor, SponsorDashboard> {
 * 
 * // Internal state ---------------------------------------------------------
 * 
 * @Autowired
 * private SponsorSponsorDashboardRepository repository;
 * 
 * // AbstractService interface ----------------------------------------------
 * 
 * 
 * @Override
 * public void authorise() {
 * super.getResponse().setAuthorised(true);
 * }
 * 
 * @Override
 * public void load() {
 * int sponsorId;
 * 
 * sponsorId = super.getRequest().getPrincipal().getActiveRoleId();
 * 
 * SponsorDashboard dashboard;
 * int numberOfInvoicesTaxLessOrEqual21;
 * int numberOfSponsorshipsWithLink;
 * 
 * numberOfInvoicesTaxLessOrEqual21 = this.repository.findNumberOfInvoicesTaxLessOrEqual21(sponsorId);
 * numberOfSponsorshipsWithLink = this.repository.findNumberOfSponsorshipsWithLink(sponsorId);
 * 
 * dashboard = new SponsorDashboard();
 * dashboard.setNumberOfInvoicesTaxLessOrEqual21(numberOfInvoicesTaxLessOrEqual21);
 * dashboard.setNumberOfSponsorshipsWithLink(numberOfSponsorshipsWithLink);
 * 
 * super.getBuffer().addData(dashboard);
 * }
 * 
 * @Override
 * public void unbind(final SponsorDashboard object) {
 * Dataset dataset;
 * 
 * dataset = super.unbind(object, //
 * "numberOfInvoicesTaxLessOrEqual21", "numberOfSponsorshipsWithLink");
 * 
 * super.getResponse().addData(dataset);
 * 
 * }
 * 
 * }
 */
