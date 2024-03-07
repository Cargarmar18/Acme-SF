
package acme.entities.sponsorships;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import javax.persistence.Transient;
import javax.validation.constraints.Digits;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import acme.client.data.datatypes.Money;
import acme.datatypes.SponsorshipDatatype;
import acme.entities.project.Project;
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
	private String				code;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@PastOrPresent
	private Date				moment;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date				startSponsor;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date				endSponsor;

	@NotNull
	@Min(0)
	private Money				amount;

	@NotNull
	private SponsorshipDatatype	sponsorshipType;

	@Email
	private String				email;

	@URL
	private String				moreInfo;

	@Column(unique = true)
	@NotBlank
	@Pattern(regexp = "IN-[0-9]{4}-[0-9]{4}")
	private String				invoiceCode;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@PastOrPresent
	private Date				registrationTime;

	// TO DO applying constraint of 1 month
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date				dueDate;

	// 	TO DO add constraint positive or 0 on money datatype
	@NotNull
	private Money				invoiceQuantity;

	@Digits(integer = 3, fraction = 2)
	@Min(0)
	@Max(100)
	private double				tax;

	@URL
	private String				InvoiceLink;

	// Derived attributes -----------------------------------------------------


	@Transient
	public double totalAmount() {
		return this.invoiceQuantity.getAmount() + this.tax * this.invoiceQuantity.getAmount() / 100;
	}

	// Relationships ----------------------------------------------------------

}
