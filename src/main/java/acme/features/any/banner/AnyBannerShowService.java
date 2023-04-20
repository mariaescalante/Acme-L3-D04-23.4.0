
package acme.features.any.banner;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Banner;
import acme.framework.components.accounts.Any;
import acme.framework.components.models.Tuple;
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
		Collection<Integer> banners;
		List<Integer> ls;
		int id;
		int random;
		banners = this.repository.findAvailableBanners(Date.from(Instant.now()));
		ls = new ArrayList<>(banners);
		random = (int) Math.floor(Math.random() * (ls.size() - 2));
		id = ls.get(random);
		final Banner object = this.repository.findOneBannerById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void unbind(final Banner object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "instantiationMoment", "start", "end", "picture", "slogan", "url");

		super.getResponse().setData(tuple);
	}

}
