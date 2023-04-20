
package acme.entities;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import acme.framework.data.AbstractEntity;
import acme.roles.Assistant;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Tutorial extends AbstractEntity {

	protected static final long	serialVersionUID	= 1L;

	@Column(unique = true)
	@NotBlank
	@Pattern(regexp = "[A-Z]{1,3} [0-9]{3}")
	protected String			code;

	@NotBlank
	@Length(max = 75)
	protected String			title;

	@NotBlank
	@Length(max = 100)
	protected String			abstract$;

	@NotBlank
	@Length(max = 100)
	protected String			goal;

	protected boolean			draftMode;


	public Double totalTime(final Collection<Session> sessions) {
		double res = 0.0;
		if (!sessions.isEmpty())
			for (final Session sesion : sessions) {
				final Date a = sesion.getStartTime();
				final Date b = sesion.getEndTime();
				double h = 0.0;
				double m = 0.0;
				h = Math.abs(b.getTime() / 3600000 - a.getTime() / 3600000);
				m = Math.abs(b.getTime() / 60000 - a.getTime() / 60000) % 60;
				final double horasMinutos = m / 60;
				res += h + horasMinutos;
			}
		return res;
	}


	@NotNull
	@Valid
	@ManyToOne
	protected Assistant	assistant;

	@NotNull
	@Valid
	@ManyToOne
	protected Course	course;
}
