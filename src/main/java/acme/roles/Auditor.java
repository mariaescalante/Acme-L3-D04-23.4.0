
package acme.roles;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.framework.data.AbstractRole;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter

public class Auditor extends AbstractRole {

	/*-id: Long {NotBlank, Unique}
	-firm: String {NotBlank, Length(1,75)}
	-professional_id: String {NotBlank, Length(1,25)}
	-certifications: String {NotBlank, Length(1,100)}
	-link: String {URL}*/

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@NotBlank
	@Length(min = 1, max = 75)
	protected String			firm;

	@NotBlank
	@Length(min = 1, max = 25)
	protected String			professionalId;

	@NotBlank
	@Length(min = 1, max = 100)
	protected String			certifications;

	@URL
	protected String			link;

}
