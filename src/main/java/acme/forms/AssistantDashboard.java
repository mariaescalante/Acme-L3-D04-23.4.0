
package acme.forms;

import java.util.Map;

import acme.datatypes.SessionType;
import acme.framework.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssistantDashboard extends AbstractForm {

	protected static final long	serialVersionUID	= 1L;

	Map<SessionType, Integer>	totalTutorials;

	Double						averageDurationSessions;

	Double						deviationDurationSessions;

	Double						minimumDurationSession;

	Double						maximumDurationSession;

	Double						averageDurationTutorials;

	Double						deviationDurationTutorials;

	Double						minimumDurationTutorial;

	Double						maximumDurationTutorial;
}
