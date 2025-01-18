package es.upsa.ssbbdd2.trabajo.domain.exceptions;

public class TrabajoExceptions extends Exception {
     public TrabajoExceptions() {
    }

    public TrabajoExceptions(Throwable cause) {
        super(cause);
    }

    public TrabajoExceptions(String message) {
        super(message);
    }

    public TrabajoExceptions(String message, Throwable cause) {
        super(message, cause);
    }

    public TrabajoExceptions(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
