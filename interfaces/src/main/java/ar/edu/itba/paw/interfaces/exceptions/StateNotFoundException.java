package ar.edu.itba.paw.interfaces.exceptions;

public class StateNotFoundException extends RuntimeException{
    public StateNotFoundException() {
        super();
    }

    public StateNotFoundException(String message) {
        super(message);
    }
}
