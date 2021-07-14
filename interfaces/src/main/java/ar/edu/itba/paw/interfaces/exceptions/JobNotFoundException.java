package ar.edu.itba.paw.interfaces.exceptions;

public class JobNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public JobNotFoundException() {
        super();
    }

    public JobNotFoundException(String message) {
        super(message);
    }
}
