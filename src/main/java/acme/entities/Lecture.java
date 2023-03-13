
package acme.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.framework.data.AbstractEntity;
import acme.roles.Lecturer;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Lecture extends AbstractEntity {

	protected static final long	serialVersionUID	= 1L;

	@NotBlank
	@Length(max = 75)
	protected String			title;

	@NotBlank
	@Length(max = 100)
	protected String			abstrac;

	@Positive
	protected Integer			time;

	@Temporal(TemporalType.TIMESTAMP)
	protected Date				availabilityPeriod;

	@NotBlank
	@Length(max = 100)
	protected String			body;

	protected Boolean			theoretical;

	@URL
	protected String			link;

	@ManyToOne(optional = false)
	protected Lecturer			lecturer;

	@ManyToOne(optional = false)
	protected Course			course;
}
