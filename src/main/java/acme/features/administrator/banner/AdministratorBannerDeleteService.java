
package acme.features.administrator.banner;

public class AdministratorBannerDeleteService {
	/*
	 * // Internal state ---------------------------------------------------------
	 * 
	 * @Autowired
	 * private AdministratorBannerRepository repository;
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
	 * Banner object;
	 * int id;
	 * 
	 * id = super.getRequest().getData("id", int.class);
	 * object = this.repository.findOneBannerById(id);
	 * 
	 * super.getBuffer().addData(object);
	 * }
	 * 
	 * @Override
	 * public void bind(final Banner object) {
	 * assert object != null;
	 * 
	 * super.bind(object, "name", "description", "moreInfo");
	 * }
	 * 
	 * @Override
	 * public void validate(final Banner object) {
	 * assert object != null;
	 * 
	 * boolean status;
	 * int id, numberProxies, numberJobs;
	 * 
	 * id = super.getRequest().getData("id", int.class);
	 * numberProxies = this.repository.findNumberProxiesByContractorId(id);
	 * numberJobs = this.repository.findNumberJobsByContractorId(id);
	 * 
	 * status = numberProxies == 0 && numberJobs == 0;
	 * 
	 * super.state(status, "*", "administrator.Banner.delete.Banner-linked");
	 * }
	 * 
	 * @Override
	 * public void perform(final Banner object) {
	 * assert object != null;
	 * 
	 * this.repository.delete(object);
	 * }
	 * 
	 * @Override
	 * public void unbind(final Banner object) {
	 * assert object != null;
	 * 
	 * Dataset dataset;
	 * 
	 * dataset = super.unbind(object, "name", "description", "moreInfo");
	 * 
	 * super.getResponse().addData(dataset);
	 * }
	 * 
	 */
}
