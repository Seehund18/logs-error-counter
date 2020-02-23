/**
 * Эксепшен кидаемый из класса ErrorCounter
 */
public class ErrorCounterException extends RuntimeException {
    public ErrorCounterException() {
        super();
    }

    public ErrorCounterException(String message) {
        super(message);
    }

    public ErrorCounterException(String message, Throwable cause) {
        super(message, cause);
    }
}
