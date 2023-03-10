
package acme.entities;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;

import acme.framework.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class DashboardAuditing extends AbstractForm {
	/*-id: Long {NotBlank, Unique}	
	-total_number_of_audits: Integer
	-average_number: Double
	-deviation_number: Double
	-maxium_number: Integer
	-minium_number: Integer
	-average_time: Double
	-deviation_time: Double
	-maxium_time: Integer
	-minium_time: Integer*/

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@NotBlank
	@Column(unique = true)
	protected String			totalNumberOfAudits;

	protected Double			average;

	protected Double			deviation;

	protected Integer			maxiumTime;

	protected Integer			total_number_of_audits;

	protected Double			average_number;

	protected Double			deviation_number;

	protected Integer			maxium_number;

	protected Integer			minium_number;

	protected Double			average_time;

	protected Double			deviation_time;

	protected Integer			maxium_time;

	protected Integer			minium_time;

}
