
package acme.features.any.banner;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Banner;
import acme.framework.components.accounts.Any;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;

@Service
public class AnyBannerShowService extends AbstractService<Any, Banner> {

	@Autowired
	protected AnyBannerRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Banner object;
		Collection<Integer> banners;
		List<Integer> ls;
		int id;
		int random;
		banners = this.repository.findAvailableBanners(MomentHelper.getCurrentMoment());
		ls = new ArrayList<>(banners);
		if (!ls.isEmpty()) {
			random = (int) Math.round(Math.random() * (ls.size() - 1));
			id = ls.get(random);
			object = this.repository.findOneBannerById(id);
		} else
			object = new Banner();
		super.getBuffer().setData(object);
	}

	@Override
	public void unbind(final Banner object) {
		assert object != null;
		Collection<Integer> banners;
		banners = this.repository.findAvailableBanners(MomentHelper.getCurrentMoment());

		Tuple tuple;

		tuple = super.unbind(object, "instantiationMoment", "start", "end", "picture", "slogan", "url");
		if (banners.size() == 0)
			tuple.put("hay", false);
		else
			tuple.put("hay", true);
		super.getResponse().setData(tuple);
	}
}
