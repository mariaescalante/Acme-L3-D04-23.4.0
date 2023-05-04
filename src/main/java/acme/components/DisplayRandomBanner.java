
package acme.components;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import acme.entities.Banner;
import acme.features.any.banner.AnyBannerRepository;
import acme.framework.helpers.MomentHelper;

@ControllerAdvice
public class DisplayRandomBanner {

	@Autowired
	protected AnyBannerRepository repository;


	@ModelAttribute("banner")
	public Banner getRandomBanner() {
		Banner object;
		Collection<Integer> banners;
		List<Integer> ls;
		int id;
		banners = this.repository.findAvailableBanners(MomentHelper.getCurrentMoment());
		ls = new ArrayList<>(banners);
		id = this.getRandomInteger(ls);
		object = this.repository.findOneBannerById(id);
		return object;
	}

	public Integer getRandomInteger(final List<Integer> ls) {
		int res;
		int random;
		random = (int) Math.round(Math.random() * (ls.size() - 1));
		res = ls.get(random);
		return res;
	}

}
