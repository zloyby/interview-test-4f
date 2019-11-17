package cz.finance.hr.test.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import javax.annotation.Nonnull;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * GPS latitude/longitude.
 */
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GpsCoordinates {

    @Nonnull
    private Double latitude;

    @Nonnull
    private Double longtitude;

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(Double longtitude) {
        this.longtitude = longtitude;
    }

}
