
package acme.roles;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractRole;

public class Developer extends AbstractRole {

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@NotBlank
	@Length(max = 75)
	private String				degree;

	@NotBlank
	@Length(max = 100)
	private String				specialisation;

	@NotBlank
	@Length(max = 100)
	private String				skills;

	@NotNull
	@Email
	private String				email;

	@URL
	private String				link;
	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

}
