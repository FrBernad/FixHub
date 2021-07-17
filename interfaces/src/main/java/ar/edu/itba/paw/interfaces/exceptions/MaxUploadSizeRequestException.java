package ar.edu.itba.paw.interfaces.exceptions;

public class MaxUploadSizeRequestException extends RuntimeException {
    public MaxUploadSizeRequestException() {
        super("exception.MaxUploadSizeRequest");
    }
}
