package by.zloy.db.browser.zeaver.dbcp;

import java.util.Map;
import javax.sql.DataSource;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class RoutingDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        return ConnectionIdHolder.getConnectionId();
    }

    public void setDefaultDataSource(DataSource dataSource) {
        setDefaultTargetDataSource(dataSource);
    }

    public void setDataSources(Map<Object, Object> dataSources) {
        setTargetDataSources(dataSources);
    }
}
