
package acme.roles;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Assistant {

	protected static final long	serialVersionUID	= 1L;

	@NotBlank
	@Length(max = 75)
	protected String			supervisor;

	@NotBlank
	@Length(max = 100)
	protected String			expertiseFields;

	@NotBlank
	@Length(max = 100)
	protected String			summary;

	@URL
	protected String			link;
}
