package by.zloy.db.browser.zeaver.dbcp;

import by.zloy.db.browser.zeaver.exception.NotFoundException;
import java.util.Optional;

public class ConnectionIdHolder {

    private static final ThreadLocal<Long> contextHolder = new ThreadLocal<>();

    static Long getConnectionId() {
        final Long id = contextHolder.get();
        return Optional.ofNullable(id).orElseThrow(NotFoundException::new);
    }

    public static void setConnectionId(Long id) {
        contextHolder.set(id);
    }

    public static void removeConnectionId() {
        contextHolder.remove();
    }
}
