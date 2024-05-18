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

package acme.components;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.helpers.MomentHelper;
import acme.client.repositories.AbstractRepository;
import acme.entities.banner.Banner;

@Repository
public interface BannerRepository extends AbstractRepository {

	@Query("select count(a) from Banner a where a.startDisplay < :moment and a.endDisplay > :moment ")
	int countBanners(Date moment);

	@Query("select a from Banner a where a.startDisplay < :moment and a.endDisplay > :moment")
	List<Banner> findManyBanners(PageRequest pageRequest, Date moment);

	default Banner findRandomBanner() {
		Banner result;
		int count;
		PageRequest page;
		List<Banner> list;
		Date moment = MomentHelper.getCurrentMoment();

		count = this.countBanners(moment);
		if (count == 0)
			result = null;
		else {

			page = PageRequest.of(1, 1, Sort.by(Direction.ASC, "id"));
			list = this.findManyBanners(page, moment);
			result = list.isEmpty() ? null : list.get(0);
		}

		return result;
	}

}
