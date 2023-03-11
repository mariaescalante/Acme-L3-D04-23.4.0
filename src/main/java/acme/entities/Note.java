
package acme.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
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
public class Note extends AbstractEntity {

	protected static final long	serialVersionUID	= 1L;

	@Temporal(TemporalType.TIMESTAMP)
	@Past
	protected Date				instantiationMoment;

	@NotBlank
	@Length(max = 75)
	protected String			title;

	/*
	 * FALTA POR HACER::
	 * Que el autor se compute como:
	 * “〈username〉 - 〈surname, name〉”, where “〈user-name〉”
	 * denotes the username of the principal who has posted the
	 * note and “〈surname, name〉” denotes his or her full name.
	 */
	@NotBlank
	@Length(max = 75)
	protected String			author;

	@NotBlank
	@Length(max = 100)
	protected String			message;

	@Email
	protected String			emailAddress;

	@URL
	protected String			link;
}
