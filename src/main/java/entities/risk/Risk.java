
package entities.risk;

import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
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

	// optional link
	//description not blank shorter than 101
	@Column(unique = true)
	@NotBlank
	@Pattern(regexp = "R-[0-9]{3}", message = "The code must follow R-XXX pattern")
	private double				reference;

	@NotBlank
	@Length(max = 100)
	private String				title;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@PastOrPresent
	private Date				idDate;

	@NotNull

	@Valid
	private BigInteger			salary;

	@Range(min = 0, max = 100)
	@Digits(integer = 3, fraction = 2)
	private double				score;

	@NotBlank
	@Length(max = 255)
	private String				description;

	@URL
	@Length(max = 255)
	private String				moreInfo;

	private boolean				draftMode;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

}
