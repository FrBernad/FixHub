package ar.edu.itba.paw.interfaces.exceptions;

public class IllegalContentTypeException extends RuntimeException{

    public IllegalContentTypeException() {
        super();
    }

    public IllegalContentTypeException(String message) {
        super(message);
    }
}
