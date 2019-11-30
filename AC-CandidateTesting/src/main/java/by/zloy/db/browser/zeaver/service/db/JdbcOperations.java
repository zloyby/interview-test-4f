package by.zloy.db.browser.zeaver.service.db;

public interface JdbcOperations {

    default String getCommonDatabaseInfo() {
        return "select version();";
    }

    String getDatabases();

    default String getSchemas() {
        return "select * from information_schema.schemata;";
    }

    String getTables(String schema);

    String getColumns(String schema, String table);

    default String getData(String schema, String table, Long limit, Long offset) {
        return "select * from " + schema + "." + table + " limit " + limit + " offset " + offset + ";";
    }

    default String getTableStatistics(String schema) {
        return "select true as NOT_IMPLEMENTED";
    }

    default String getColumnStatistics(String schema, String table) {
        return "select true as NOT_IMPLEMENTED";
    }
}
