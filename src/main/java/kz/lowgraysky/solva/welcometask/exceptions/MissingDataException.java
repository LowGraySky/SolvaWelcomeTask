package kz.lowgraysky.solva.welcometask.exceptions;

public class MissingDataException extends RuntimeException{

    public MissingDataException(String message) {
        super(message);
    }

    public MissingDataException(String message, Throwable cause) {
        super(message, cause);
    }

    public MissingDataException(Throwable cause) {
        super(cause);
    }
}
