
package acme.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import acme.framework.data.AbstractEntity;
import acme.roles.Auditor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter

public class Audit extends AbstractEntity {

	/*-id: Long {NotBlank, Unique}
	-code: String {NotBlank, Unique, Transient}
	-conclusion: String {NotBlank, Length(1,100)}
	-strong points: String {NotBlank, Length(1,100)}
	-weak points: String {NotBlank, Length(1,100)}
	-mark: Integer{Transient}
	*/

	// Serialisation identifier -----------------------------------------------

	protected static final long		serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@NotBlank
	@Column(unique = true)
	@Pattern(regexp = "[A-Z]{1,3}[0-9][0-9]{3}")
	protected String				code;

	@NotBlank
	@Length(min = 1, max = 100)
	protected String				conclusion;

	@NotBlank
	@Length(min = 1, max = 100)
	protected String				strongPoints;

	@NotBlank
	@Length(min = 1, max = 100)
	protected String				weakPoints;

	@Transient
	protected Mark					mark;

	// Relationships ----------------------------------------------------------

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	protected Auditor				auditor;

	/*
	 * @NotNull
	 * 
	 * @Valid
	 * 
	 * @ManyToOne(optional = false)
	 * protected Course course;
	 */

	@NotNull
	@Valid
	@OneToMany()
	protected List<AuditingRecords>	records;


	public Mark getMark() {
		final List<Mark> marks = new ArrayList<>();
		for (final AuditingRecords record : this.records)
			marks.add(record.getMark());
		Integer maximoNumRepeticiones = 0;
		Double moda = 0.;
		final List<Double> marks2 = new ArrayList<>();
		for (final Mark mark : marks)
			if (mark == Mark.Fminus)
				marks2.add(0.);
			else if (this.mark == Mark.F)
				marks2.add(1.);
			else if (this.mark == Mark.C)
				marks2.add(2.);
			else if (this.mark == Mark.B)
				marks2.add(3.);
			else if (this.mark == Mark.A)
				marks2.add(4.);
			else if (this.mark == Mark.Aplus)
				marks2.add(5.);
		for (Integer i = 0; i < marks2.size(); i++) {
			int numRepeticiones = 0;
			for (int j = 0; j < marks2.size(); j++) {
				if (marks2.get(i) == marks2.get(j))
					numRepeticiones++;
				if (numRepeticiones > maximoNumRepeticiones) {
					moda = marks2.get(i);
					maximoNumRepeticiones = numRepeticiones;
				}
			}
		}
		Mark res = Mark.Fminus;
		if (Math.round(moda) == 1)
			res = Mark.F;
		else if (Math.round(moda) == 2)
			res = Mark.C;
		else if (Math.round(moda) == 3)
			res = Mark.B;
		else if (Math.round(moda) == 4)
			res = Mark.A;
		else if (Math.round(moda) == 5)
			res = Mark.Aplus;
		return res;
	}

}
