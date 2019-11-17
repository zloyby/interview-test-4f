package cz.finance.hr.test.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import javax.annotation.Nonnull;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class City {

	/**
	 * Unique city ID (generated).
	 */
	@Nonnull
	Long id;

	/**
	 * City name.
	 */
	@Nonnull
	String name;

	/**
	 * GPS coordinates of the city center.
	 */
	@Nonnull
	GpsCoordinates gpsCoordinates;

	/**
	 * Reference to {@link Region#getId()}.
	 */
	@Nonnull
	Long regionId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public GpsCoordinates getGpsCoordinates() {
		return gpsCoordinates;
	}

	public void setGpsCoordinates(GpsCoordinates gpsCoordinates) {
		this.gpsCoordinates = gpsCoordinates;
	}

	public Long getRegionId() {
		return regionId;
	}

	public void setRegionId(Long regionId) {
		this.regionId = regionId;
	}
}
