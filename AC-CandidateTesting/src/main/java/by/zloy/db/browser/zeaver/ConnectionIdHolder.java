package by.zloy.db.browser.zeaver;

import java.util.Optional;

public class ConnectionIdHolder {

    private static final ThreadLocal<Long> contextHolder = new ThreadLocal<>();

    public static Long getConnectionId() {
        final Long id = contextHolder.get();
        return Optional.ofNullable(id).orElse(-1L);
    }

    public static void setConnectionId(Long id) {
        contextHolder.set(id);
    }

    public static void removeConnectionId() {
        contextHolder.remove();
    }
}
