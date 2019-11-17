package cz.finance.hr.test.core.repository;

import cz.finance.hr.test.core.model.CountryEntity;
import cz.finance.hr.test.core.model.RegionEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegionRepository extends CrudRepository<RegionEntity, Long> {

    Optional<RegionEntity> findByNameAndCountry(String name, CountryEntity country);

    @Query("select re from RegionEntity re " +
            "join fetch re.country co " +
            "where co.id = :countryId")
    List<RegionEntity> findAllByCountryId(Long countryId);
}
