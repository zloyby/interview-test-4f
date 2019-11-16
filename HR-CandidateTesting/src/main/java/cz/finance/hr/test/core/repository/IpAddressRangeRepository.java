package cz.finance.hr.test.core.repository;

import cz.finance.hr.test.core.model.IpAddressRangeEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IpAddressRangeRepository extends CrudRepository<IpAddressRangeEntity, Long> {

    @Query("select ip from IpAddressRangeEntity ip " +
            "left join fetch ip.gpsCoordinatesEntity gp " +
            "left join fetch gp.city ci " +
            "where ip.from < :ip and ip.to > :ip")
    Optional<IpAddressRangeEntity> findIpInsideRange(Long ip);

    @Query("select ip from IpAddressRangeEntity ip " +
            "left join fetch ip.gpsCoordinatesEntity gp " +
            "left join fetch gp.city ci " +
            "left join fetch ci.region re " +
            "left join fetch re.country " +
            "where ip.from < :ip and ip.to > :ip")
    Optional<IpAddressRangeEntity> findIpInsideRangeWithFetch(Long ip);
}
