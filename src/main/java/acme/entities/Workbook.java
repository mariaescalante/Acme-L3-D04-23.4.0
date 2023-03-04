
package acme.entities;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import acme.framework.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Workbook extends AbstractEntity {

	protected static final long	serialVersionUID	= 1L;

	@OneToOne
	@JoinColumn(name = "enrolments_id")
	protected Enrolment			enrolment;

}
