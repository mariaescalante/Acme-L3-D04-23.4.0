
package acme.entities;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import acme.framework.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Membership extends AbstractEntity {

	protected static final long	serialVersionUID	= 1L;

	protected String			title;

	@NotNull
	@ManyToOne(optional = false)
	protected Course			course;

	@NotNull
	@ManyToOne(optional = false)
	protected Lecture			lecture;
}
