package errors;

public class AccessLevelCantRent extends Throwable{

    public AccessLevelCantRent() {
    }

    public AccessLevelCantRent(String message) {
        super(message);
    }

    public AccessLevelCantRent(String message, Throwable cause) {
        super(message, cause);
    }

    public AccessLevelCantRent(Throwable cause) {
        super(cause);
    }

    public AccessLevelCantRent(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
