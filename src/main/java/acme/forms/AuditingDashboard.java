
package acme.forms;

import java.util.Map;

import acme.entities.CourseType;
import acme.framework.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class AuditingDashboard extends AbstractForm {
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

	Map<CourseType, Integer>	total_number_of_audits;
	Double						average_number;
	Double						deviation_number;
	Integer						maxium_number;
	Integer						minium_number;
	Double						average_time;
	Double						deviation_time;
	Integer						maxium_time;
	Integer						minium_time;

}
