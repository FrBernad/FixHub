package ar.edu.itba.paw.interfaces.exceptions;

public class NoContactFoundException extends  RuntimeException{

    public NoContactFoundException() {
        super("exception.NoContactFoundException");
    }
}
