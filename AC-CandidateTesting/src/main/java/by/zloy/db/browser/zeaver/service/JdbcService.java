package by.zloy.db.browser.zeaver.service;

import by.zloy.db.browser.zeaver.ConnectionIdHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.Objects;

@Service
@Slf4j
@Transactional
public class JdbcService {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcService(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Boolean test(Long id) {
        ConnectionIdHolder.setConnectionId(id);

        final SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet("select 1");

        ConnectionIdHolder.setConnectionId(-1L);

        return Objects.nonNull(sqlRowSet);
    }
}
