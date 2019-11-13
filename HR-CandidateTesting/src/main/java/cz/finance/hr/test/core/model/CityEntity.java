package cz.finance.hr.test.core.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "City")
public class CityEntity {

    /**
     * Unique city ID (generated).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * City name.
     */
    @Column
    private String name;

    /**
     * GPS coordinates of the city center.
     */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "gps_coordinates")
    private GpsCoordinatesEntity gpsCoordinates;

    /**
     * Reference to {@link RegionEntity#getId()}.
     */
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "region_id")
    private RegionEntity regionId;
}
