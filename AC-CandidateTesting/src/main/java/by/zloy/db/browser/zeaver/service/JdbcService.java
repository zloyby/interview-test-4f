package by.zloy.db.browser.zeaver.service;

import by.zloy.db.browser.zeaver.dbcp.ConnectionIdHolder;
import java.util.Objects;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional
public class JdbcService {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcService(@Qualifier("dynamicRoutingDataSource") DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Boolean test(Long connectionId) {
        ConnectionIdHolder.setConnectionId(connectionId);

        final SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet("select 1");

        ConnectionIdHolder.removeConnectionId();

        return Objects.nonNull(sqlRowSet);
    }
}
