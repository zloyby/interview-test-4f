package cz.finance.hr.test.core.repository;

import cz.finance.hr.test.core.model.IpAddressRangeEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IpAddressRangeRepository extends CrudRepository<IpAddressRangeEntity, Long> {

    @Query("select ip from IpAddressRangeEntity ip where ip.from < ?0 and ip.to > ?0")
    Optional<IpAddressRangeEntity> findIpInsideRange(Long ip);

}
