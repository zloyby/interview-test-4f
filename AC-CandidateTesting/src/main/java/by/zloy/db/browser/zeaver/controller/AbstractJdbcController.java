package by.zloy.db.browser.zeaver.controller;

import by.zloy.db.browser.zeaver.model.Connection;
import by.zloy.db.browser.zeaver.model.Driver;
import by.zloy.db.browser.zeaver.service.ConnectionService;
import by.zloy.db.browser.zeaver.service.JdbcService;
import by.zloy.db.browser.zeaver.service.db.JdbcOperations;
import by.zloy.db.browser.zeaver.service.db.JdbcOperationsFactory;
import by.zloy.db.browser.zeaver.service.dbcp.DataSourceBeanFactory;
import java.util.List;
import java.util.function.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
abstract public class AbstractJdbcController {

    private final DataSourceBeanFactory dataSourceBeanFactory;
    private final ConnectionService connectionService;
    private final JdbcService jdbcService;

    @Autowired
    public AbstractJdbcController(ConnectionService connectionService,
                                  JdbcService jdbcService,
                                  DataSourceBeanFactory dataSourceBeanFactory) {
        this.connectionService = connectionService;
        this.jdbcService = jdbcService;
        this.dataSourceBeanFactory = dataSourceBeanFactory;
    }

    JdbcOperations findJdbcBridge(Long id) {
        final Connection connection = connectionService.getConnection(id);
        dataSourceBeanFactory.addIfNotExist(id, connection);
        final Driver driver = connection.getDriver();

        Supplier<JdbcOperationsFactory> factorySupplier = JdbcOperationsFactory::new;
        return factorySupplier.get().getJdbcOperations(driver);
    }

    ResponseEntity<List> getQueryResult(Long id, Supplier<String> supplier) {
        final List queryResult = jdbcService.executeQuery(id, supplier.get());
        return ResponseEntity.ok(queryResult);
    }

    ResponseEntity<List> getQueryResult(Long id, String query) {
        final List queryResult = jdbcService.executeQuery(id, query);
        return ResponseEntity.ok(queryResult);
    }
}
