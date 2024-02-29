
package acme.entities.banner;

import java.io.Serializable;
import java.time.Instant;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Banner extends AbstractEntity implements Serializable {

	private static final long	serialVersionUID	= 1L;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@PastOrPresent
	private Instant				updateMoment;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Instant				startDisplay;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Instant				endDisplay;

	@URL
	@NotBlank
	private String				pictureLink;

	@NotBlank
	@Size(max = 75)
	private String				slogan;

	@URL
	@NotBlank
	private String				targetLink;

	// Additional business logic or relationships can be added as needed

}
