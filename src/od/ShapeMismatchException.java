package od;

public class ShapeMismatchException extends Exception {
    public ShapeMismatchException() {
    }

    public ShapeMismatchException(String message) {
        super(message);
    }

    public ShapeMismatchException(String message, Throwable cause) {
        super(message, cause);
    }

    public ShapeMismatchException(Throwable cause) {
        super(cause);
    }

    public ShapeMismatchException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
