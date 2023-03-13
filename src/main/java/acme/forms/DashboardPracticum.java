
package acme.forms;

import javax.persistence.Entity;

import acme.framework.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class DashboardPracticum extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	int[]						total_theory_lectures;

	Double						average_session_time;

	Double						session_time_deviation;

	Double						session_time_minimum;

	Double						session_time_maximum;

	Double						average_practica_time;

	Double						practica_time_deviation;

	Double						practica_time_minimum;

	Double						practica_time_maximum;
}
