package ar.edu.itba.paw.interfaces.exceptions;

public class IllegalOperationException extends RuntimeException{

    public IllegalOperationException() {
        super();
    }

    public IllegalOperationException(String message) {
        super(message);
    }
}
