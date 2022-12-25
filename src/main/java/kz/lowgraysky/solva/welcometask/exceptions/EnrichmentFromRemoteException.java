package kz.lowgraysky.solva.welcometask.exceptions;

public class EnrichmentFromRemoteException extends RuntimeException{

    public EnrichmentFromRemoteException(String message) {
        super(message);
    }

    public EnrichmentFromRemoteException(String message, Throwable cause) {
        super(message, cause);
    }
}
