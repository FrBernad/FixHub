package ar.edu.itba.paw.webapp.dto.response;

import ar.edu.itba.paw.webapp.dto.customValidations.FieldsValueMatch;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Size;


@FieldsValueMatch(
      field = "password",
      fieldMatch = "confirmPassword"
    )
public class ResetPasswordDto {

    @NotEmpty
    @Size(min = 6)
    private String password;

    @NotEmpty
    @Size(min = 6)
    private String confirmPassword;

    @NotEmpty
    private String token;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
