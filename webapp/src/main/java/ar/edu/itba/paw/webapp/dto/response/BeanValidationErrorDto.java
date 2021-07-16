package ar.edu.itba.paw.webapp.dto.response;

public class BeanValidationErrorDto {

    private String message;

    public BeanValidationErrorDto() {
        // Jersey, do not use
    }

    public BeanValidationErrorDto(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

