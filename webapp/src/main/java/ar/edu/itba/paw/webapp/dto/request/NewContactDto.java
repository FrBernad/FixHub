package ar.edu.itba.paw.webapp.dto.request;


import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class NewContactDto {

    @NotEmpty(message="{NotEmpty.newContact.state}")
    @Size(max = 50, message="{Size.newContact.state}")
    @Pattern(regexp = "^[a-zA-ZàáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆŠŽ∂ð ,.'-]*$", message="{Pattern.newContact.state}")
    private String state;

    @NotEmpty(message="{NotEmpty.newContact.city}")
    @Size(max = 50, message="{Size.newContact.city}")
    @Pattern(regexp = "^[0-9a-zA-ZàáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆŠŽ∂ð ,.'-]*$", message="{Pattern.newContact.city}")
    private String city;

    @NotEmpty(message="{NotEmpty.newContact.street}")
    @Size(max = 50, message="{Size.newContact.street}")
    @Pattern(regexp = "^[0-9a-zA-ZàáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆŠŽ∂ð ,.'-]*$", message="{Pattern.newContact.street}")
    private String street;

    @NotEmpty(message="{NotEmpty.newContact.addressNumber}")
    @Pattern(regexp = "[0-9]{0,9}", message="{Pattern.newContact.addressNumber}")
    private String addressNumber;

    @Pattern(regexp = "[0-9]{0,9}", message="{Pattern.newContact.floor}")
    private String floor;

    @Pattern(regexp = "[A-Za-z0-9]{0,30}", message="{Pattern.newContact.departmentNumber}")
    private String departmentNumber;

    @NotEmpty(message="")
    @Size(max = 300, message="{NotEmpty.newContact.message}")
    private String message;

    @NotNull(message="")
    private String contactInfoId;

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

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getAddressNumber() {
        return addressNumber;
    }

    public void setAddressNumber(String addressNumber) {
        this.addressNumber = addressNumber;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getDepartmentNumber() {
        return departmentNumber;
    }

    public void setDepartmentNumber(String departmentNumber) {
        this.departmentNumber = departmentNumber;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getContactInfoId() {
        return contactInfoId;
    }

    public void setContactInfoId(String contactInfoId) {
        this.contactInfoId = contactInfoId;
    }
}
