package cz.finance.hr.test.core.repository;

import cz.finance.hr.test.core.model.CountryEntity;
import cz.finance.hr.test.core.model.RegionEntity;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegionRepository extends CrudRepository<RegionEntity, Long> {

    Optional<RegionEntity> findByNameAndCountry(String name, CountryEntity country);
}
