
package acme.entities;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.framework.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter

public class Bulletin extends AbstractEntity {

	// Attributes -------------------------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	@NotBlank
	@Past
	@Temporal(TemporalType.TIMESTAMP)
	protected String			instantiationMoment;

	@NotBlank
	@Length(min = 1, max = 75)
	protected String			title;

	@NotBlank
	@Length(min = 1, max = 100)
	protected String			message;

	protected Boolean			flag;

	@URL
	protected String			url;

}
