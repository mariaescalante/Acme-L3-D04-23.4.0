
package acme.forms;

import acme.framework.data.AbstractForm;

public class LecturerDashboard extends AbstractForm {

	protected static final long	serialVersionUID	= 1L;

	Integer						total_theory_lectures;
	Integer						total_hands_on_lectures;
	Double						average_lecture_learning_time;
	Integer						lecture_learning_time_minimum;
	Integer						lecture_learning_time_maximum;
	Double						lecture_learning_time_deviation;
	Double						average_course_learning_time;
	Integer						course_learning_time_minimum;
	Integer						course_learning_time_maximum;
	Double						course_learning_time_deviation;
}
