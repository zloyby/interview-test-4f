package cz.finance.hr.test.core.repository;

import cz.finance.hr.test.core.model.CityEntity;
import cz.finance.hr.test.core.model.GpsCoordinatesEntity;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GpsCoordinatesRepository extends CrudRepository<GpsCoordinatesEntity, Long> {

    Optional<GpsCoordinatesEntity> findByLatitudeAndLongtitudeAndCity(Double lat, Double lon, CityEntity city);

}
