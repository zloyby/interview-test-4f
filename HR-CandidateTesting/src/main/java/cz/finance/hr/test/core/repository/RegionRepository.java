package cz.finance.hr.test.core.repository;

import cz.finance.hr.test.core.model.RegionEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegionRepository extends CrudRepository<RegionEntity, Long> {

}
