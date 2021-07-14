package ar.edu.itba.paw.interfaces.exceptions;

public class ScheduleNotFoundException extends RuntimeException{
    public ScheduleNotFoundException() {
        super();
    }

    public ScheduleNotFoundException(String message) {
        super(message);
    }
}
