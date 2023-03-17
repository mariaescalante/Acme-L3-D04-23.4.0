
package acme.forms;

import java.util.Map;

import acme.datatypes.ActivityType;
import acme.framework.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentDashboard extends AbstractForm {

	protected static final long	serialVersionUID	= 1L;

	Map<ActivityType, Integer>	toalActivitiesByType;
	Double						avegagePeriodOfActivities;
	Double						deviationPeriodOfActivities;
	Double						maximumPeriodOfActivities;
	Double						minimumPeriodOfActivities;
	Double						avegageLearningTimeOfCourses;
	Double						deviationLearningTimeOfCourses;
	Double						maximumLearningTimeOfCourses;
	Double						minimumLearningTimeOfCourses;

}
