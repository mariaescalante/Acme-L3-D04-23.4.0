
package acme.entities;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import acme.framework.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Session extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@NotBlank
	@Size(max = 75)
	protected String			title;

	@NotBlank
	@Size(max = 100)
	protected String			abstractText;

	@FutureOrPresent
	protected LocalDate			startDate;

	@FutureOrPresent
	protected LocalDate			endDate;
	//faltan los @
	protected String			furtherInformationLink;
	//faltan los @
	protected Practicum			practicum;
}
