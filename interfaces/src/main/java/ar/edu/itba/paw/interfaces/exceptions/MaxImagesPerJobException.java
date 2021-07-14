package ar.edu.itba.paw.interfaces.exceptions;

public class MaxImagesPerJobException extends RuntimeException{

    public MaxImagesPerJobException() {
        super();
    }

    public MaxImagesPerJobException(String message) {
        super(message);
    }
}
