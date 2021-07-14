package ar.edu.itba.paw.interfaces.exceptions;

public class ReviewNotFoundException extends RuntimeException {
    public ReviewNotFoundException() {
        super();
    }

    public ReviewNotFoundException(String message) {
        super(message);
    }
}
