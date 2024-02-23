
package acme.entities.sponsorships;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.URL;
import org.springframework.lang.Nullable;

import acme.client.data.AbstractEntity;
import acme.datatypes.SponsorshipDatatype;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Sponsorship extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@Column(unique = true)
	@NotBlank
	@Pattern(regexp = "[A-Z]{1,3}-[0-9]{3}")
	private String				reference;

	@Temporal(TemporalType.TIMESTAMP)
	@PastOrPresent
	private Date				moment;

	@Temporal(TemporalType.TIMESTAMP)
	@Future
	private Date				duration;

	@Min(0)
	private double				amount;

	private SponsorshipDatatype	sponsorshipType;

	@Nullable
	@Email
	private String				email;

	@Nullable
	@URL
	private String				moreInfo;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------
	//relationship with project?
}
