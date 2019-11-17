package cz.finance.hr.test.core.repository;

import cz.finance.hr.test.core.model.CityEntity;
import cz.finance.hr.test.core.model.RegionEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends CrudRepository<CityEntity, Long> {

    Optional<CityEntity> findByNameAndRegion(String name, RegionEntity region);

    @Query("select ci from CityEntity ci " +
            "join fetch ci.region re " +
            "where re.id = :regionId")
    List<CityEntity> findAllByRegionId(Long regionId);

    @Query("select ci from CityEntity ci " +
            "join fetch ci.region re " +
            "join fetch re.country co " +
            "where co.id = :countryId")
    List<CityEntity> findAllByCountryId(Long countryId);
}
