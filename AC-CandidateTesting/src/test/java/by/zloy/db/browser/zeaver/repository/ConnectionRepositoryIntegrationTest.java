package by.zloy.db.browser.zeaver.repository;

import by.zloy.db.browser.zeaver.model.Connection;
import by.zloy.db.browser.zeaver.model.Driver;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ConnectionRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ConnectionRepository connectionRepository;

    @Test
    public void findByName_returnConnection() {
        final String testName = "Test DB Name";
        final Connection connection = Connection.builder()
                .name(testName).driver(Driver.MYSQL).host("127.0.0.1").port("3306")
                .database("zeaver").user("root").password("root").build();
        entityManager.persist(connection);
        entityManager.flush();

        final Connection found = connectionRepository.findByName(testName);

        Assertions.assertThat(found.getName()).isEqualTo(connection.getName());
    }
}
