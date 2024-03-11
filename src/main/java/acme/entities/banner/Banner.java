
package acme.entities.banner;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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

	private static final long	serialVersionUID	= 1L;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@PastOrPresent
	private Date				instatiationUpdateMoment;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date				startDisplay;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date				endDisplay;

	@Length(max = 255)
	@URL
	@NotBlank
	private String				pictureLink;

	@NotBlank
	@Length(max = 75)
	private String				slogan;

	@Length(max = 255)
	@URL
	@NotBlank
	private String				targetLink;

	// Additional business logic or relationships can be added as needed

}
