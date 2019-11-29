package by.zloy.db.browser.zeaver.service.db;

import org.springframework.stereotype.Component;

@Component
public class PostgresqlJdbcOperations implements JdbcOperations<String> {

    @Override
    public String getCommonDatabaseInfo() {
        return "SELECT version();";
    }

    @Override
    public String getDatabases() {
        return "SELECT datname, datdba, datcollate, datctype, datistemplate, datallowconn, datconnlimit, " +
                "datlastsysoid, datfrozenxid, datminmxid, dattablespace FROM pg_database;";
    }

    @Override
    public String getSchemas() {
        return "SELECT * FROM information_schema.schemata;";
    }

    @Override
    public String getTables(String schema) {
        return "SELECT * FROM information_schema.tables WHERE table_schema = '" + schema + "';";
    }

    @Override
    public String getColumns(String schema, String table) {
        return "SELECT  \n" +
                "    f.attnum AS number,  \n" +
                "    f.attname AS name,  \n" +
                "    f.attnum,  \n" +
                "    f.attnotnull AS notnull,  \n" +
                "    pg_catalog.format_type(f.atttypid,f.atttypmod) AS type,  \n" +
                "    CASE  \n" +
                "        WHEN p.contype = 'p' THEN 't'  \n" +
                "        ELSE 'f'  \n" +
                "    END AS primarykey,  \n" +
                "    CASE  \n" +
                "        WHEN p.contype = 'u' THEN 't'  \n" +
                "        ELSE 'f'\n" +
                "    END AS uniquekey,\n" +
                "    CASE\n" +
                "        WHEN p.contype = 'f' THEN g.relname\n" +
                "    END AS foreignkey,\n" +
                "    CASE\n" +
                "        WHEN p.contype = 'f' THEN p.confkey\n" +
                "    END AS foreignkey_fieldnum,\n" +
                "    CASE\n" +
                "        WHEN p.contype = 'f' THEN g.relname\n" +
                "    END AS foreignkey,\n" +
                "    CASE\n" +
                "        WHEN p.contype = 'f' THEN p.conkey\n" +
                "    END AS foreignkey_connnum,\n" +
                "    CASE\n" +
                "        WHEN f.atthasdef = 't' THEN d.adsrc\n" +
                "    END AS default\n" +
                "FROM pg_attribute f  \n" +
                "    JOIN pg_class c ON c.oid = f.attrelid  \n" +
                "    JOIN pg_type t ON t.oid = f.atttypid  \n" +
                "    LEFT JOIN pg_attrdef d ON d.adrelid = c.oid AND d.adnum = f.attnum  \n" +
                "    LEFT JOIN pg_namespace n ON n.oid = c.relnamespace  \n" +
                "    LEFT JOIN pg_constraint p ON p.conrelid = c.oid AND f.attnum = ANY (p.conkey)  \n" +
                "    LEFT JOIN pg_class AS g ON p.confrelid = g.oid  \n" +
                "WHERE c.relkind = 'r'::char  \n" +
                "    AND n.nspname = '" + schema + "'  \n" +
                "    AND c.relname = '" + table + "'  \n" +
                "    AND f.attnum > 0 ORDER BY number;";
    }

    @Override
    public String getData(String schema, String table, Long limit, Long offset) {
        return "SELECT * from " + schema + "." + table + " LIMIT " + limit + " OFFSET " + offset + ";";
    }

    @Override
    public String getTableStatistics(String schema) {
        return "select true as NOT_IMPLEMENTED";
    }

    @Override
    public String getColumnStatistics(String schema, String table) {
        return "select true as NOT_IMPLEMENTED";
    }
}
