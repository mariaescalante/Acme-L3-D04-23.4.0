
package acme.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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
public class Banner extends AbstractEntity {

	protected static final long	serialVersionUID	= 1L;

	@PastOrPresent
	@Temporal(value = TemporalType.TIMESTAMP)
	protected Date				instantiationMoment;

	@Temporal(value = TemporalType.TIMESTAMP)
	protected Date				start;

	@Temporal(value = TemporalType.TIMESTAMP)
	protected Date				end;

	@URL
	protected String			picture;

	@NotBlank
	@Length(max = 75)
	protected String			slogan;

	@URL
	protected String			url;

}
