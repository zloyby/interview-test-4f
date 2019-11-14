package cz.finance.hr.test.core.repository;

import cz.finance.hr.test.core.model.GpsCoordinatesEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GpsCoordinatesRepository extends CrudRepository<GpsCoordinatesEntity, Long> {

}
