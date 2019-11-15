package cz.finance.hr.test.core.repository;

import cz.finance.hr.test.core.model.CityEntity;
import cz.finance.hr.test.core.model.RegionEntity;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends CrudRepository<CityEntity, Long> {

    Optional<CityEntity> findByNameAndRegion(String name, RegionEntity region);
}
