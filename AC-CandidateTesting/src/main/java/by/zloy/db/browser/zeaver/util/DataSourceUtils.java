package by.zloy.db.browser.zeaver.util;

import by.zloy.db.browser.zeaver.model.Connection;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.jdbc.DataSourceBuilder;

import javax.sql.DataSource;

public class DataSourceUtils {

    private DataSourceUtils() {
    }

    public static DataSource of(Connection connection) {
        return DataSourceBuilder.create()
                .username(connection.getUser())
                .password(connection.getPassword())
                .url("jdbc:" + connection.getDriver().getDriverType() +
                        "://" + connection.getHost() +
                        ":" + connection.getPort() +
                        "/" + connection.getDatabase() +
                        (StringUtils.isNotEmpty(connection.getParameters()) ? connection.getParameters() : connection.getDriver().getDefaultParameters()))
                .driverClassName(connection.getDriver().getDriverClass())
                .build();
    }
}
