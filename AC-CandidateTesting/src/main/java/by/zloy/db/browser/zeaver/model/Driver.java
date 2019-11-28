package by.zloy.db.browser.zeaver.model;

import lombok.Getter;

@Getter
public enum Driver {
    MYSQL("mysql", "com.mysql.cj.jdbc.Driver", "?useSSL=false&useUnicode=true&characterEncoding=UTF-8&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC"),
    POSTGRESQL("postgresql", "org.postgresql.Driver", "?ssl=false");

    private String driverType;
    private String driverClass;
    private String defaultParameters;

    Driver(String driverType, String driverClass, String defaultParameters) {
        this.driverType = driverType;
        this.driverClass = driverClass;
        this.defaultParameters = defaultParameters;
    }
}
