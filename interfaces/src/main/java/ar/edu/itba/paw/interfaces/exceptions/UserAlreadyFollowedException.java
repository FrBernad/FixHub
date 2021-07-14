package ar.edu.itba.paw.interfaces.exceptions;

public class UserAlreadyFollowedException extends RuntimeException{

    public UserAlreadyFollowedException() {
        super();
    }

    public UserAlreadyFollowedException(String message) {
        super(message);
    }
}
