package ar.edu.itba.paw.interfaces.exceptions;

public class ServerInternalException extends RuntimeException{
    public ServerInternalException() {
        super("exception.ServerInternalError");
    }
}
