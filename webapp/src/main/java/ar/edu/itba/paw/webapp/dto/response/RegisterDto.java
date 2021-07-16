package ar.edu.itba.paw.webapp.dto.response;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class RegisterDto {

    @NotEmpty(message = "{NotEmpty.registerDto.name}")
    @Size(max = 50, message = "{Size.registerDto.name}")
    @Pattern(regexp = "^[a-zA-ZàáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆČŠŽ∂ð ,.'-]*$", message = "{Pattern.registerDto.name}")
    private String name;

    @NotEmpty(message = "{NotEmpty.registerDto.surname}")
    @Size(max = 50, message = "{Size.registerDto.surname}")
    @Pattern(regexp = "^[a-zA-ZàáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆČŠŽ∂ð ,.'-]*$", message = "{Pattern.registerDto.surname}")
    private String surname;

    @NotEmpty(message = "{NotEmpty.registerDto.email}")
    @Size(max = 200, message = "{Size.registerDto.email}")
    @Pattern(regexp = "^([a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+)*$", message = "{Pattern.registerDto.email}")
    private String email;

    @NotEmpty(message = "{NotEmpty.registerDto.password}")
    @Size(min = 6, max = 20, message = "{Size.registerDto.password}")
    private String password;

    @NotEmpty(message = "{NotEmpty.registerDto.phoneNumber}")
    @Size(max = 15, message = "{Size.registerDto.phoneNumber}")
    /* Toma como opcionales: el prefijo internacional (54) el prefijo internacional para celulares (9) el prefijo
     de acceso a interurbanas (0) el prefijo local para celulares (15) Es obligatorio: el código de área
     (11, 2xx, 2xxx, 3xx, 3xxx, 6xx y 8xx) (no toma como válido un número local sin código de área como 4444-0000)
     */
    @Pattern(regexp = "^[+]?(?:(?:00)?549?)?0?(?:11|15|[2368]\\d)(?:(?=\\d{0,2}15)\\d{2})??\\d{8}$", message = "{Pattern.registerDto.phoneNumber}")
    private String phoneNumber;

    @NotEmpty(message = "{NotEmpty.registerDto.state}")
    @Size(max = 50, message = "{Size.registerDto.state}")
    @Pattern(regexp = "^[0-9a-zA-ZàáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆŠŽ∂ð ,.'-]*$", message = "{Pattern.registerDto.state}")
    private String state;

    @NotEmpty(message = "{NotEmpty.registerDto.city}")
    @Size(max = 50, message = "{Size.registerDto.city}")
    @Pattern(regexp = "^[0-9a-zA-ZàáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆŠŽ∂ð ,.'-]*$", message = "{Pattern.registerDto.city}")
    private String city;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
