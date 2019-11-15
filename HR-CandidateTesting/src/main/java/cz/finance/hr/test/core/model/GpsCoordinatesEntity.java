package cz.finance.hr.test.core.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "GpsCoordinates")
public class GpsCoordinatesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column
    private Double latitude;

    @Column
    private Double longtitude;

    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = true)
    @Cascade({CascadeType.SAVE_UPDATE, CascadeType.DELETE})
    @JoinColumn(name = "city_id")
    private CityEntity city;
}
