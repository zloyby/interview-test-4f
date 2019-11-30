package by.zloy.db.browser.zeaver.service;

import by.zloy.db.browser.zeaver.service.dbcp.ConnectionIdHolder;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Scope("prototype")
@Slf4j
@Transactional
public class JdbcService {

    private JdbcTemplate jdbcTemplate;

    private Function<String, List<Map<String, Object>>> executeFunction = query -> jdbcTemplate.queryForList(query);

    @Autowired
    public JdbcService(@Qualifier("dynamicDataSource") DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    //TODO: add interceptor for set/remove connectionId in ConnectionIdHolder
    public List executeQuery(Long connectionId, String query) {
        ConnectionIdHolder.setConnectionId(connectionId);

        final List<Map<String, Object>> result = executeFunction.apply(query);

        ConnectionIdHolder.removeConnectionId();
        return result;
    }
}
