package ar.edu.itba.paw.interfaces.exceptions;

public class ContactInfoNotFoundException extends RuntimeException{

    public ContactInfoNotFoundException() {
        super();
    }

    public ContactInfoNotFoundException(String message) {
        super(message);
    }
}
