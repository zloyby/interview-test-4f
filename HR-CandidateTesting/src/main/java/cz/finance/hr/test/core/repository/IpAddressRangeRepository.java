package cz.finance.hr.test.core.repository;

import cz.finance.hr.test.core.model.IpAddressRangeEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IpAddressRangeRepository extends CrudRepository<IpAddressRangeEntity, Long> {

}
