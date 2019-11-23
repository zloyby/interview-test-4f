package by.zloy.db.browser.zeaver.exception;

public class ZeaverException extends RuntimeException {
    public ZeaverException() {
    }

    public ZeaverException(String message) {
        super(message);
    }

    public ZeaverException(String message, Throwable cause) {
        super(message, cause);
    }

    public ZeaverException(Throwable cause) {
        super(cause);
    }
}

