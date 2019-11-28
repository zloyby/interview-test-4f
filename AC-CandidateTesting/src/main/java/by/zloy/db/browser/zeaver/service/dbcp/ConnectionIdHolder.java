package by.zloy.db.browser.zeaver.service.dbcp;

import by.zloy.db.browser.zeaver.exception.DatabaseConnectionException;

import java.util.Optional;

public class ConnectionIdHolder {

    private static final ThreadLocal<Long> contextHolder = new ThreadLocal<>();

    static Long getConnectionId() {
        final Long id = contextHolder.get();
        return Optional.ofNullable(id).orElseThrow(DatabaseConnectionException::new);
    }

    public static void setConnectionId(Long id) {
        contextHolder.set(id);
    }

    public static void removeConnectionId() {
        contextHolder.remove();
    }
}
