package ar.edu.itba.paw.interfaces.exceptions;

public class IllegalContactException extends RuntimeException{
    public IllegalContactException() {
        super();
    }

    public IllegalContactException(String message) {
        super(message);
    }
}
