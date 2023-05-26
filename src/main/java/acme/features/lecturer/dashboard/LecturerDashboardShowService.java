
package acme.features.lecturer.dashboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.CourseType;
import acme.forms.LecturerDashboard;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerDashboardShowService extends AbstractService<Lecturer, LecturerDashboard> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected LecturerDashboardRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		boolean status;

		status = super.getRequest().getPrincipal().hasRole(Lecturer.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		LecturerDashboard object;
		final Integer totalTheoryLectures;
		final Integer totalHandsOnLectures;
		final Double averageLectureLearningTime;
		final Integer lectureLearningTimeMinimum;
		final Integer lectureLearningTimeMaximum;
		final Double lectureLearningTimeDeviation;
		final Double averageCourseLearningTime;
		final Integer courseLearningTimeMinimum;
		final Integer courseLearningTimeMaximum;
		final Double courseLearningTimeDeviation;
		int lecturerId;

		lecturerId = super.getRequest().getPrincipal().getActiveRoleId();
		totalTheoryLectures = this.repository.totalCourseTypeLectures(lecturerId, CourseType.THEORETICAL);
		totalHandsOnLectures = this.repository.totalCourseTypeLectures(lecturerId, CourseType.HANDSON);
		averageLectureLearningTime = this.repository.averageLectureLearningTime(lecturerId);
		lectureLearningTimeMinimum = this.repository.lectureLearningTimeMinimum(lecturerId);
		lectureLearningTimeMaximum = this.repository.lectureLearningTimeMaximum(lecturerId);
		lectureLearningTimeDeviation = this.repository.lectureLearningTimeDeviation(lecturerId);
		averageCourseLearningTime = this.repository.averageCourseLearningTime(lecturerId);
		courseLearningTimeMinimum = this.repository.courseLearningTimeMinimum(lecturerId);
		courseLearningTimeMaximum = this.repository.courseLearningTimeMaximum(lecturerId);
		courseLearningTimeDeviation = this.repository.courseLearningTimeDeviation(lecturerId);
		object = new LecturerDashboard();

		object.setTotalTheoryLectures(totalTheoryLectures);
		object.setTotalHandsOnLectures(totalHandsOnLectures);
		object.setAverageLectureLearningTime(averageLectureLearningTime);
		object.setLectureLearningTimeMinimum(lectureLearningTimeMinimum);
		object.setLectureLearningTimeMaximum(lectureLearningTimeMaximum);
		object.setLectureLearningTimeDeviation(lectureLearningTimeDeviation);
		object.setAverageCourseLearningTime(averageCourseLearningTime);
		object.setCourseLearningTimeMinimum(courseLearningTimeMinimum);
		object.setCourseLearningTimeMaximum(courseLearningTimeMaximum);
		object.setCourseLearningTimeDeviation(courseLearningTimeDeviation);
		super.getBuffer().setData(object);
	}

	@Override
	public void unbind(final LecturerDashboard object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "totalTheoryLectures", "totalHandsOnLectures", "averageLectureLearningTime", "lectureLearningTimeMinimum", "lectureLearningTimeMaximum", "lectureLearningTimeDeviation", "averageCourseLearningTime",
			"courseLearningTimeMinimum", "courseLearningTimeMaximum", "courseLearningTimeDeviation");

		super.getResponse().setData(tuple);
	}

}
