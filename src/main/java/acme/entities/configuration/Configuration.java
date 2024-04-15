
package acme.entities.configuration;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import acme.client.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Configuration extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------
	@Pattern(regexp = "^[A-Z]{3}$")
	@NotBlank
	private String				defaultCurrency;

	@Pattern(regexp = "^[A-Z]{3}(,[A-Z]{3}){2}$")
	@NotBlank
	private String				acceptedCurrencies;

}
