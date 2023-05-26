
package acme.forms;

import acme.framework.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LecturerDashboard extends AbstractForm {

	protected static final long	serialVersionUID	= 1L;

	Integer						totalTheoryLectures;
	Integer						totalHandsOnLectures;
	Double						averageLectureLearningTime;
	Integer						lectureLearningTimeMinimum;
	Integer						lectureLearningTimeMaximum;
	Double						lectureLearningTimeDeviation;
	Double						averageCourseLearningTime;
	Integer						courseLearningTimeMinimum;
	Integer						courseLearningTimeMaximum;
	Double						courseLearningTimeDeviation;
}
