package cz.finance.hr.test.core.repository;

import cz.finance.hr.test.core.model.RequestsTransactionEntity;
import cz.finance.hr.test.core.model.response.IpLimitResponse;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestsTransactionRepository extends CrudRepository<RequestsTransactionEntity, Long> {

    @SuppressWarnings("SpringDataRepositoryMethodReturnTypeInspection")
    @Query("select new cz.finance.hr.test.core.model.response.IpLimitResponse ( " +
            "sum(case when ip.countryId = :countryId and ip.created > :timeToEnd then 1 else 0 end), " +
            "sum(case when ip.regionId = :regionId and ip.created > :timeToEnd then 1 else 0 end), " +
            "sum(case when ip.cityId = :cityId and ip.created > :timeToEnd then 1 else 0 end)) " +
            "from RequestsTransactionEntity ip")
    IpLimitResponse findLimitsByLast(Long countryId, Long regionId, Long cityId, Long timeToEnd);
}
