
package acme.entities;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.framework.components.datatypes.Money;
import acme.framework.data.AbstractEntity;
import acme.roles.Lecturer;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Course extends AbstractEntity {

	protected static final long	serialVersionUID	= 1L;

	@NotBlank
	@Pattern(regexp = "[A-Z]{1,3} [0-9]{3}")
	protected String			code;

	@NotBlank
	@Length(max = 75)
	protected String			title;

	@NotBlank
	@Length(max = 100)
	protected String			abstract$;

	protected CourseType		theoreticalOrHandsOn;
  
	protected Money				price;

	@URL
	protected String			link;

	@NotNull
	@ManyToOne(optional = false)
	protected Lecturer			lecturer;

	protected boolean			draftMode;

	public CourseType theoreticalOrHandsOn(final Collection<Lecture> lectures) {
		CourseType res = CourseType.HANDSON;
		double total = 0.;
		int theoretical = 0;
		if (!lectures.isEmpty()) {
			for (final Lecture lecture : lectures) {
				total = total + 1;
				if (lecture.getTheoreticalOrHandsOn().equals(CourseType.THEORETICAL))
					theoretical = theoretical + 1;
			}
			if (theoretical > total / 2)
				res = CourseType.THEORETICAL;
		}
		return res;
	}
  
	public boolean purelyTheoretical(final Collection<Lecture> lectures) {
		boolean res = false;
		double total = 0.;
		int theoretical = 0;
		if (!lectures.isEmpty()) {
			for (final Lecture lecture : lectures) {
				total = total + 1;
				if (lecture.getTheoreticalOrHandsOn().equals(CourseType.THEORETICAL))
					theoretical = theoretical + 1;
			}
			if (theoretical == total)
				res = true;
		}
		return res;
	}
}
