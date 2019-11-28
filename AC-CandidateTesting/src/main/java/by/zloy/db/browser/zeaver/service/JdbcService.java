package by.zloy.db.browser.zeaver.service;

import by.zloy.db.browser.zeaver.service.dbcp.ConnectionIdHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.Objects;

@SuppressWarnings("SqlNoDataSourceInspection")
@Service
@Scope("prototype")
@Slf4j
@Transactional
public class JdbcService {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcService(@Qualifier("dynamicDataSource") DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    //TODO: create interceptor for set/remove connectionId
    public Boolean test(Long connectionId) {
        ConnectionIdHolder.setConnectionId(connectionId);

        final SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet("select 1");

        ConnectionIdHolder.removeConnectionId();
        return Objects.nonNull(sqlRowSet);
    }
}
