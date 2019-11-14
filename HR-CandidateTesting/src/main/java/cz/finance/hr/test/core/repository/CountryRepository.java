package cz.finance.hr.test.core.repository;

import cz.finance.hr.test.core.model.CountryEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends CrudRepository<CountryEntity, Long> {

}
