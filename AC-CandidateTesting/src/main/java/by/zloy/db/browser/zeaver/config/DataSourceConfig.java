package by.zloy.db.browser.zeaver.config;

import by.zloy.db.browser.zeaver.dbcp.RoutingDataSource;
import com.zaxxer.hikari.HikariDataSource;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
@EntityScan(basePackages = "by.zloy.db.browser.zeaver.model")
public class DataSourceConfig {

    @Bean
    @Primary
    @ConfigurationProperties("default.datasource")
    public DataSourceProperties dataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean("dataSource")
    @Primary
    @ConfigurationProperties("default.datasource.configuration")
    public HikariDataSource dataSource(DataSourceProperties properties) {
        return properties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }

    @Bean("dynamicRoutingDataSource")
    public DataSource dynamicDataSource() {
        final RoutingDataSource routingDataSource = new RoutingDataSource();

        final DataSource mysqlDataSource = DataSourceBuilder.create()
                .username("root")
                .password("root")
                .url("jdbc:mysql://localhost:3306/zeaver?useSSL=false&useUnicode=true&characterEncoding=UTF-8&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC")
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .build();
        routingDataSource.setDefaultDataSource(mysqlDataSource);

        final Map<Object, Object> dataSources = new HashMap<>();
        dataSources.put(1, mysqlDataSource);
        routingDataSource.setDataSources(dataSources);
        return routingDataSource;
    }
}
