
package acme.features.administrator.offer;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.Offer;
import acme.framework.components.accounts.Administrator;
import acme.framework.controllers.AbstractController;

@Controller
public class AuthenticatedOfferController extends AbstractController<Administrator, Offer> {

	@Autowired
	protected AuthenticatedOfferListAllService	listAllService;

	@Autowired
	protected AuthenticatedOfferShowService		showService;


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("list", this.listAllService);
		super.addBasicCommand("show", this.showService);
	}
}
