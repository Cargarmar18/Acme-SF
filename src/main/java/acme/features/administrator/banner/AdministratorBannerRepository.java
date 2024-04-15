
/*
 * BannerRepository.java
 *
 * Copyright (C) 2012-2024 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.administrator.banner;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.banner.Banner;
import acme.entities.claim.Claim;

@Repository
public interface AdministratorBannerRepository extends AbstractRepository {

	@Query("select count(a) from Banner a")
	int countBanners();

	@Query("select a from Banner a")
	Collection<Banner> findAllBanners();
	
	@Query("select a from Banner a where a.id = :id")
	Claim findBannerById(int id);

}
