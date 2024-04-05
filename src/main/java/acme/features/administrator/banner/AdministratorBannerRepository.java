
package acme.features.administrator.banner;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.banner.Banner;

@Repository
public interface AdministratorBannerRepository extends AbstractRepository {

	@Query("select b from Banner b")
	Collection<Banner> findAllBanner();

}
