
package acme.entities;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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
	-period: Duration
	-mark: String
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

	protected Double			period;

	protected Mark				mark;

	@URL
	protected String			link;

	// Relationships ----------------------------------------------------------

	@NotNull
	@Valid
	@OneToOne(optional = false)
	protected Audit				audit;

}
