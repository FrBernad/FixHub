package ar.edu.itba.paw.interfaces.exceptions;

public class NotificationNotFoundException extends RuntimeException{

    public NotificationNotFoundException() {
        super();
    }

    public NotificationNotFoundException(String message) {
        super(message);
    }
}
