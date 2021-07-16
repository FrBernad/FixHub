package ar.edu.itba.paw.webapp.dto.response;

import javax.xml.bind.annotation.XmlType;

@XmlType(name="")
public class ContactDto {

    private String state;
    private String city;
    private String street;
    private String addressNumber;
    private String floor;
    private String departmentNumber;
    private String message;
    private String contactInfoId;
    private String userId;

    public ContactDto() {
        // Used by Jersey
    }

    public ContactDto(
        String state,
        String city,
        String street,
        String addressNumber,
        String floor,
        String departmentNumber,
        String message,
        String contactInfoId,
        String userId
    )
    {
        this.state = state;
        this.city = city;
        this.street = street;
        this.addressNumber = addressNumber;
        this.floor = floor;
        this.departmentNumber = departmentNumber;
        this.message = message;
        this.contactInfoId = contactInfoId;
        this.userId = userId;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
