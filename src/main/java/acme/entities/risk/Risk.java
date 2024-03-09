
package acme.entities.risk;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Risk extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@Column(unique = true)
	@NotBlank
	@Pattern(regexp = "^R-[0-9]{3}$", message = "validation.Risk.reference")
	private String				reference;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@PastOrPresent
	private Date				idDate;

	@NotNull
	@Min(0)
	@Max(100)
	@Digits(integer = 3, fraction = 2)
	private double				impact;

	@NotNull
	@Min(0)
	@Max(100)
	@Digits(integer = 3, fraction = 2)
	private double				probability;

	@NotBlank
	@Length(max = 100)
	private String				description;

	@Size(max = 255)
	@URL
	private String				link;


	// Derived attributes -----------------------------------------------------
	@Transient
	public double value() {
		Double result;

		result = this.impact * this.probability / 100.0;

		return result;
	}
	// Relationships ----------------------------------------------------------

}
