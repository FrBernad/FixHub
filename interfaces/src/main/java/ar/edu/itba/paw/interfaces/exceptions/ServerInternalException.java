package ar.edu.itba.paw.interfaces.exceptions;

public class ServerInternalException extends RuntimeException{
    public ServerInternalException() {
        super();
    }

    public ServerInternalException(String message) {
        super(message);
    }
}
