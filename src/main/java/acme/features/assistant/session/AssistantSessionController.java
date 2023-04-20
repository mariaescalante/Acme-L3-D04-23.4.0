
package acme.features.assistant.session;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.Session;
import acme.framework.controllers.AbstractController;
import acme.roles.Assistant;

@Controller
public class AssistantSessionController extends AbstractController<Assistant, Session> {

	@Autowired
	protected AssistantSessionListService	listService;

	@Autowired
	protected AssistantSessionShowService	showService;

	@Autowired
	protected AssistantSessionDeleteService	deleteService;

	@Autowired
	protected AssistantSessionUpdateService	updateService;

	@Autowired
	protected AssistantSessionCreateService	createService;


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("list", this.listService);
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("create", this.createService);
		super.addBasicCommand("update", this.updateService);
		super.addBasicCommand("delete", this.deleteService);
	}
}
