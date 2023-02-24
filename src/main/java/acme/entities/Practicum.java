
package acme.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import acme.framework.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Practicum extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@NotBlank
	@Pattern(regexp = "[A-Z]{1,3}[0-9][0-9]{3}")
	protected String				code;

	@NotBlank
	@Length(max = 75)
	protected String				title;

	@NotBlank
	@Length(max = 100)
	protected String				abstractText;

	@NotBlank
	@Length(max = 100)
	protected String				goals;
	//faltan los @
	protected int					estimatedTotalTime;
	//falta los @
	protected List<Session>		sessions			= new ArrayList<>();

}
