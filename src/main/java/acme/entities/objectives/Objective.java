
package acme.entities.objectives;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Objective extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@NotNull
	@PastOrPresent
	@Temporal(TemporalType.TIMESTAMP)
	private Date				instantiationMoment;

	@NotBlank
	@Length(max = 75)
	private String				title;

	@NotBlank
	@Length(max = 100)
	private String				description;

	@NotNull
	private PriorityValue		priority;

	private boolean				critical;

	//TO DO: momento de inicio debe ser posterior al momento de instanciación
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date				beginningMoment;

	// TO DO: implement minimum duration of an hour, finalMoment after beginningMoment
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date				finalMoment;

	@URL
	@Length(max = 255)
	private String				link;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

}
