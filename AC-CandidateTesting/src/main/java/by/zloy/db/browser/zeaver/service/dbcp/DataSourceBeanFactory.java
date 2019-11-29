package by.zloy.db.browser.zeaver.service.dbcp;

import by.zloy.db.browser.zeaver.model.Connection;
import by.zloy.db.browser.zeaver.util.DataSourceUtils;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Component
@NoArgsConstructor
@AllArgsConstructor
public class DataSourceBeanFactory {

    private Map<Object, DataSource> dataSources;

    public void addIfNotExist(Object id, Connection connection) {
        final Map<Object, DataSource> dataSources = getDataSources();
        if (!dataSources.containsKey(id)) {
            dataSources.put(id, DataSourceUtils.of(connection));
        }
    }

    //TODO: add Scheduler - remove connection after 10 min of inactivity
    public void deleteIfExist(Object id) {
        getDataSources().remove(id);
    }

    Map<Object, DataSource> getDataSources() {
        if (Objects.isNull(dataSources)) {
            dataSources = new HashMap<>();
        }
        return dataSources;
    }
}
