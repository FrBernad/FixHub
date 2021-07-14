package ar.edu.itba.paw.interfaces.exceptions;

public class NoContactFoundException extends  RuntimeException{

    public NoContactFoundException() {
        super();
    }

    public NoContactFoundException(String message) {
        super(message);
    }
}
