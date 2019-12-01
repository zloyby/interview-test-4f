package by.zloy.db.browser.zeaver.service;

import by.zloy.db.browser.zeaver.exception.NotFoundException;
import by.zloy.db.browser.zeaver.model.Connection;
import by.zloy.db.browser.zeaver.model.Driver;
import by.zloy.db.browser.zeaver.repository.ConnectionRepository;
import by.zloy.db.browser.zeaver.service.dbcp.DataSourceBeanFactory;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@ContextConfiguration(classes = ConnectionServiceTest.TestContextConfig.class)
public class ConnectionServiceTest {

    private final String testName = "Test DB Name";
    @Autowired
    private ConnectionRepository connectionRepository;
    @Autowired
    private ConnectionService connectionService;

    @Before
    public void setUp() {
        final Connection connection = Connection.builder().id(1L)
                .name(testName).driver(Driver.MYSQL).host("127.0.0.1").port("3306")
                .database("zeaver").user("root").password("root").build();

        Mockito.when(connectionRepository.findById(1L)).thenReturn(Optional.of(connection));
    }

    @Test
    public void getConnectionById_foundEntityWithSameName() {
        Connection found = connectionService.getConnection(1L);

        Assertions.assertThat(found.getName()).isEqualTo(testName);
    }

    @Test(expected = NotFoundException.class)
    public void getConnectionByWrongId_throwNotFoundException() {
        connectionService.getConnection(100L);
    }

    @TestConfiguration
    protected static class TestContextConfig {

        @Bean
        public ConnectionRepository connectionRepository() {
            return Mockito.mock(ConnectionRepository.class);
        }

        @Bean
        public DataSourceBeanFactory dataSourceBeanFactory() {
            return Mockito.mock(DataSourceBeanFactory.class);
        }

        @Bean
        public ConnectionService connectionService() {
            return new ConnectionService(connectionRepository(), dataSourceBeanFactory());
        }
    }
}