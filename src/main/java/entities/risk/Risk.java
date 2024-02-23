
package entities.risk;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;
import org.springframework.lang.Nullable;

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
	@Pattern(regexp = "R-[0-9]{3}")
	private String				reference;

	@Temporal(TemporalType.TIMESTAMP)
	@PastOrPresent
	private Date				idDate;

	@Min(0)
	private double				impact;

	@Min(0)
	@Max(100)
	private double				probability;

	@NotBlank
	@Length(max = 101)
	private String				description;

	@Nullable
	@URL
	private String				optLink;


	// Derived attributes -----------------------------------------------------
	@Transient
	public double value() {
		Double result;

		result = this.impact * this.probability;

		return result;
	}
	// Relationships ----------------------------------------------------------

}
