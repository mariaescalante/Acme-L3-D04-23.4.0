
package acme.forms;

import java.util.Map;

import acme.framework.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdministratorDashboard extends AbstractForm {

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Cambiar Attributes -------------------------------------------------------------

	Map<String, Integer>		principalsByRole;
	Integer						peepsWithEmailAndLink;
	Map<Boolean, Integer>		criticalAndNonCriticalBulletins;
	Double						averageBudgetOffers;
	Double						minimumBudgetOffers;
	Double						maximumBudgetOffers;
	Double						deviationBudgetOffers;
	Double						averageNumberOfNotes10weeks;
	Double						minimumNumberOfNotes10weeks;
	Double						maximumNumberOfNotes10weeks;
	Double						deviationNumberOfNotes10weeks;

}
