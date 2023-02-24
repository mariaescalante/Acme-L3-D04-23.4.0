
package acme.entities;

import javax.persistence.Entity;

import acme.framework.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Dashboard extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	//falta el primero

	protected double			average_session_time;

	protected double			session_time_deviation;

	protected double			session_time_minimum;

	protected double			session_time_maximum;

	protected double			average_practica_time;

	protected double			practica_time_deviation;

	protected double			practica_time_minimum;

	protected double			practica_time_maximum;
}
