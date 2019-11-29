package by.zloy.db.browser.zeaver.service.db;

public interface JdbcOperations<T> {

    T getCommonDatabaseInfo();

    T getDatabases();

    T getSchemas();

    T getTables(String schema);

    T getColumns(String schema, String table);

    T getData(String schema, String table, Long limit, Long offset);

    T getTableStatistics(String schema);

    T getColumnStatistics(String schema, String table);
}
