
package acme.entities;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import acme.framework.data.AbstractEntity;
import acme.roles.Company;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Practicum extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@NotBlank
	@Pattern(regexp = "[A-Z]{1,3}[0-9]{3}")
	@Column(unique = true)
	protected String			code;

	@NotBlank
	@Length(max = 75)
	protected String			title;

	@NotBlank
	@Length(max = 100)
	protected String			abstract$;

	@NotBlank
	@Length(max = 100)
	protected String			goals;

	@Digits(integer = 3, fraction = 2)
	protected Double			estimatedTotalTime;

	@NotNull
	@ManyToOne(optional = false)
	protected Company			company;

	@NotNull
	@ManyToOne(optional = false)
	protected Course			course;

	protected boolean			draftMode;


	public Double totalTime(final Collection<SessionPracticum> t) {
		double res = 0.0;
		double n = 0;
		for (final SessionPracticum p : t) {
			n = p.getEndDate().toInstant().getEpochSecond() - p.getStartDate().toInstant().getEpochSecond();
			res += n / 3600;
		}
		return res;

	}

}
