package by.zloy.db.browser.zeaver.service.db;

import by.zloy.db.browser.zeaver.model.Driver;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;
import org.springframework.stereotype.Component;

@Component
public class JdbcOperationsFactory {

    private final static Map<Driver, Supplier<JdbcOperations>> map = new HashMap<>();

    static {
        map.put(Driver.POSTGRESQL, PostgresqlJdbcOperations::new);
        map.put(Driver.MYSQL, MysqlJdbcOperations::new);
    }

    public JdbcOperations getJdbcOperations(Driver driver) {
        Supplier<JdbcOperations> supplier = map.get(driver);
        if (!Objects.isNull(supplier)) {
            return supplier.get();
        }
        throw new IllegalArgumentException("No such Driver " + driver.name());
    }
}
