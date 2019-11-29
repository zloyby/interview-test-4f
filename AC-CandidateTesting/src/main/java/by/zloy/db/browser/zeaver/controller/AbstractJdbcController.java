package by.zloy.db.browser.zeaver.controller;

import by.zloy.db.browser.zeaver.model.Connection;
import by.zloy.db.browser.zeaver.service.ConnectionService;
import by.zloy.db.browser.zeaver.service.JdbcService;
import by.zloy.db.browser.zeaver.service.db.JdbcOperations;
import by.zloy.db.browser.zeaver.service.db.PostgresqlJdbcOperations;
import by.zloy.db.browser.zeaver.service.dbcp.DataSourceBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
abstract public class AbstractJdbcController {

    final JdbcOperations<String> jdbcOperations;
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

        //TODO: add JdbcOperationsFactory and MysqlJdbcOperations
        this.jdbcOperations = new PostgresqlJdbcOperations();
    }

    ResponseEntity<List> getQueryResult(Long id, String preparedQuery) {
        final Connection connection = connectionService.getConnection(id);
        dataSourceBeanFactory.addIfNotExist(id, connection);

        final List queryResult = jdbcService.executeQuery(id, preparedQuery);

        return ResponseEntity.ok(queryResult);
    }
}
