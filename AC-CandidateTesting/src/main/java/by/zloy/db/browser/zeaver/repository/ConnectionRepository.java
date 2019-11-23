package by.zloy.db.browser.zeaver.repository;

import by.zloy.db.browser.zeaver.model.Connection;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConnectionRepository extends PagingAndSortingRepository<Connection, Long> {

    Connection findByName(String name);

}
