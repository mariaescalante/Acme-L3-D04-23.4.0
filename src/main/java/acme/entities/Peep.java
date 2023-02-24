
package acme.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.framework.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Peep extends AbstractEntity {

	protected static final long	serialVersionUID	= 1L;

	@PastOrPresent
	protected Date				instantiationMoment;

	@NotBlank
	@Length(max = 75)
	protected String			title;

	@Email
	protected String			email;

	@NotBlank
	@Length(max = 75)
	protected String			nickname;

	@URL
	protected String			url;

	@NotBlank
	@Length(max = 100)
	protected String			message;

}
