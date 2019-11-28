package by.zloy.db.browser.zeaver.service.dbcp;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.sql.DataSource;
import java.util.HashMap;

@Component("dynamicDataSource")
public class RoutingDataSource extends AbstractRoutingDataSource {

    private DataSourceBeanFactory dataSourceBeanFactory;

    public RoutingDataSource(DataSourceBeanFactory dataSourceBeanFactory) {
        this.dataSourceBeanFactory = dataSourceBeanFactory;
        setTargetDataSources(new HashMap<>());
    }

    @Override
    protected Object determineCurrentLookupKey() {
        return ConnectionIdHolder.getConnectionId();
    }

    @Override
    protected DataSource determineTargetDataSource() {
        Assert.notNull(dataSourceBeanFactory, "DataSourceFactory not initialized");
        Object lookupKey = determineCurrentLookupKey();
        Assert.notNull(lookupKey, "Cannot determine lookup key for DataSource");
        DataSource dataSource = dataSourceBeanFactory.getDataSources().get(lookupKey);
        if (dataSource == null) {
            throw new IllegalStateException("Cannot determine target DataSource for lookup key [" + lookupKey + "]");
        }
        return dataSource;
    }
}
