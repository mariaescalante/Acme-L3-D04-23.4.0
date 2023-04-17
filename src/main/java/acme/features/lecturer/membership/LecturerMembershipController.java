
package acme.features.lecturer.membership;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.Membership;
import acme.framework.controllers.AbstractController;
import acme.roles.Lecturer;

@Controller
public class LecturerMembershipController extends AbstractController<Lecturer, Membership> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected LecturerMembershipListCourseService	listCourseService;

	@Autowired
	protected LecturerMembershipListMineService		listMineService;

	@Autowired
	protected LecturerMembershipShowService			showService;

	@Autowired
	protected LecturerMembershipCreateService		createService;

	@Autowired
	protected LecturerMembershipDeleteService		deleteService;

	// Constructors -----------------------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("create", this.createService);
		super.addBasicCommand("delete", this.deleteService);

		super.addCustomCommand("list-course", "list", this.listCourseService);
		super.addCustomCommand("list-mine", "list", this.listMineService);
	}

}
