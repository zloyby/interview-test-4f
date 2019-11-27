package by.zloy.db.browser.zeaver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class ZeaverApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZeaverApplication.class, args);
    }

//    @Bean("dataSource")
//    public DataSource getDataSource() {
//        final RoutingDataSource routingDataSource = new RoutingDataSource();
//
//        final DataSource defaultDataSource = DataSourceBuilder.create()
//                .url("jdbc:h2:mem:testdb")
//                .driverClassName("org.h2.Driver")
//                .username("sa")
//                .password(StringUtils.EMPTY)
//                .build();
//        final Map<Object, Object> dataSources = new HashMap<>();
//        dataSources.put(-1, defaultDataSource);
//
//        routingDataSource.setDataSources(dataSources);
//        return routingDataSource;
//    }
}
