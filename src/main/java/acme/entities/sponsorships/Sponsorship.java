
package acme.entities.sponsorships;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import acme.client.data.datatypes.Money;
import acme.entities.project.Project;
import acme.roles.Sponsor;
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
	@Pattern(regexp = "^[A-Z]{1,3}-[0-9]{3}$", message = "{validation.Sponsorship.reference}")
	private String				code;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@PastOrPresent
	private Date				moment;

	//TO DO: start sponsor must be after the instantiationMoment and before the startSponsor 
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date				startSponsor;

	//TO DO: constraint sponsors should be at least of 1 month long and endSponsor should be after startSponsor (talked in the forum)
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date				endSponsor;

	//to do constraint that can not be smaller than 0
	@NotNull
	private Money				amount;

	@NotNull
	private SponsorshipType		sponsorshipType;

	@Email
	@Length(max = 255)
	private String				email;

	@Length(max = 255)
	@URL
	private String				moreInfo;

	private boolean				draftMode;

	// Relationships ----------------------------------------------------------

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	private Project				project;

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	private Sponsor				sponsor;
}
