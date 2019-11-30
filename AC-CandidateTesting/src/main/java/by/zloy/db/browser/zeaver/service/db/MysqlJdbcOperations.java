package by.zloy.db.browser.zeaver.service.db;

import org.springframework.stereotype.Component;

@Component
public class MysqlJdbcOperations implements JdbcOperations {

    @Override
    public String getCommonDatabaseInfo() {
        return "show variables like '%version%';";
    }

    @Override
    public String getDatabases() {
        //In MySQL schema is synonymous with database
        return getSchemas();
    }

    @Override
    public String getTables(String schema) {
        return "SELECT * FROM information_schema.tables where table_schema = '" + schema + "';";
    }

    @Override
    public String getColumns(String schema, String table) {
        return "SELECT * FROM information_schema.columns \n" +
                " WHERE table_schema = '" + schema + "' AND table_name = '" + table + "';";
    }
}
