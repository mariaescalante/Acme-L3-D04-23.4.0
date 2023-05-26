
package acme.entities;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.datatypes.Mark;
import acme.framework.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter

public class AuditingRecords extends AbstractEntity {

	/*-id: Long {NotBlank, Unique}	
	-subject: String {NotBlank, Length(1,75)}
	-assessment: String {NotBlank, Length(1,100)}
	-period: Double
	-mark: Mark
	-link: String {URL}*/

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@NotBlank
	@Length(min = 1, max = 75)
	protected String			subject;

	@NotBlank
	@Length(min = 1, max = 100)
	protected String			assessment;

	@NotNull
	@Past
	@Temporal(value = TemporalType.TIMESTAMP)
	protected Date				startDate;

	@NotNull
	@Past
	@Temporal(value = TemporalType.TIMESTAMP)
	protected Date				endDate;

	protected Mark				mark;

	protected boolean			correction;

	@URL
	protected String			link;

	// Relationships ----------------------------------------------------------

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	protected Audit				audit;


	public Double period() {
		double res;
		final Date start = this.startDate;
		final Date end = this.endDate;
		final Long st = TimeUnit.MILLISECONDS.toMinutes(start.getTime());
		final Long et = TimeUnit.MILLISECONDS.toMinutes(end.getTime());
		res = Double.parseDouble(et.toString()) / 60 - Double.parseDouble(st.toString()) / 60;
		return res;
	}
}
