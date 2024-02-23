
package acme.entities.banner;

import java.time.Instant;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Banner extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@Temporal(TemporalType.TIMESTAMP)
	@PastOrPresent
	private Date				instUpdMoment;

	@Temporal(TemporalType.TIMESTAMP)
	@Future
	private Date				dispPeriod;

	@URL
	@Length(max = 255)
	private String				pictureLink;

	@NotBlank
	@Length(max = 76)
	private String				slogan;

	@URL
	private String				link;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------


	// Setter modificado para incluir la lógica de validación del execution period
	public void setDispPeriod(final Date dispPeriod) {
		if (this.isValidPeriod(dispPeriod))
			this.dispPeriod = dispPeriod;
		else
			throw new IllegalArgumentException("El execution period no cumple con la duración mínima requerida.");
	}

	private boolean isValidPeriod(final Date dispPeriod) {
		if (dispPeriod == null)
			return false;

		Instant now = Instant.now();

		Instant inicioExecutionPeriod = dispPeriod.toInstant();

		long duracionMinimaSegundos = 7 * 24 * 60 * 60;

		return inicioExecutionPeriod.plusSeconds(duracionMinimaSegundos).isBefore(now);
	}
}
