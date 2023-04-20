
package acme.entities;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import acme.framework.data.AbstractEntity;
import acme.roles.Student;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Enrolment extends AbstractEntity {

	protected static final long	serialVersionUID	= 1L;

	@Column(unique = true)
	@Pattern(regexp = "[A-Z]{1,3} [0-9]{3}")
	protected String			code;

	@NotBlank
	@Length(max = 75)
	protected String			motivation;

	@NotBlank
	@Length(max = 100)
	protected String			goals;

	protected String			workbook;

	@ManyToOne()
	@Valid
	protected Student			student;

	@ManyToOne()
	@Valid
	protected Course			course;

	protected boolean			draftMode;

	protected String			creditCard;


	public Double workTime(final Collection<Activity> activities) {
		double res = 0.0;
		if (!activities.isEmpty())
			for (final Activity activity : activities) {
				Double hours = 0.;
				final double minutes = 0.;
				final Date startDate = activity.getStartDate();
				final Date endDate = activity.getEndDate();
				hours = Math.abs(endDate.getTime() / 3600000. - startDate.getTime() / 3600000.);
				res += hours;
			}
		return res;
	}

}
