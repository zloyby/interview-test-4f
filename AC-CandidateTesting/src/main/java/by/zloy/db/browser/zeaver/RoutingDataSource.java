package by.zloy.db.browser.zeaver;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.util.Map;

public class RoutingDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        return ConnectionIdHolder.getConnectionId();
    }

    public void setDataSources(Map<Object, Object> dataSources) {
        setDefaultTargetDataSource(dataSources.get(-1L));
        setTargetDataSources(dataSources);
    }
}
