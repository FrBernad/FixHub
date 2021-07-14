package ar.edu.itba.paw.interfaces.exceptions;

public class ReviewException extends RuntimeException{

    public ReviewException() {
        super();
    }

    public ReviewException(String message) {
        super(message);
    }
}
