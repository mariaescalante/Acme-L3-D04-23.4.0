
package acme.entities;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.framework.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Session extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@NotBlank
	@Length(max = 75)
	protected String			title;

	@NotBlank
	@Length(max = 100)
	protected String			abstractText;

	@Temporal(value = TemporalType.TIMESTAMP)
	protected LocalDate			startDate;

	@Temporal(value = TemporalType.TIMESTAMP)
	protected LocalDate			endDate;

	@URL
	protected String			furtherInformationLink;

	@ManyToOne(optional = false)
	protected Practicum			practicum;
}
