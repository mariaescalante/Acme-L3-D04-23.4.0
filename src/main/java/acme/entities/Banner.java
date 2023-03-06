
package acme.entities;

import java.time.LocalDate;

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
public class Banner extends AbstractEntity {

	protected static final long	serialVersionUID	= 1L;

	@Past
	@Temporal(value = TemporalType.TIMESTAMP)
	protected LocalDate			instantiationMoment;

	@Temporal(value = TemporalType.TIMESTAMP)
	protected LocalDate			start;

	@Temporal(value = TemporalType.TIMESTAMP)
	protected LocalDate			end;

	@URL
	protected String			picture;

	@NotBlank
	@Length(max = 75)
	protected String			slogan;

	@URL
	protected String			url;
}
