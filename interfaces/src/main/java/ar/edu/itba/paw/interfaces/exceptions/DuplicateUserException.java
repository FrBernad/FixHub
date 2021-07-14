package ar.edu.itba.paw.interfaces.exceptions;

public class DuplicateUserException extends Exception{

    public DuplicateUserException() {
        super();
    }

    public DuplicateUserException(String message) {
        super(message);
    }
}
