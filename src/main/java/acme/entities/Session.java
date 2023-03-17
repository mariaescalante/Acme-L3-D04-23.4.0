
package acme.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.datatypes.SessionType;
import acme.framework.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Session extends AbstractEntity {

	protected static final long	serialVersionUID	= 1L;

	@NotBlank
	@Length(max = 75)
	protected String			title;

	@NotBlank
	@Length(max = 100)
	protected String			abstract$;

	protected SessionType		indication;

	// at least one day ahead, from one up to five hour long
	@Temporal(TemporalType.TIMESTAMP)
	protected Date				startTime;

	@Temporal(TemporalType.TIMESTAMP)
	protected Date				endTime;

	@URL
	protected String			link;

	@ManyToOne
	protected Tutorial			tutorial;
}
