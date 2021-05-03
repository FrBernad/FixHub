package ar.edu.itba.paw.models;

import java.util.Objects;

public class ContactInfo {

    private Long contactInfoId, userId;

    private String state, city, street, addressNumber, floor, departmentNumber;

    public ContactInfo(Long contactInfoId, long userId, String state, String city, String street, String addressNumber, String floor, String departmentNumber) {
        this.contactInfoId = contactInfoId;
        this.userId = userId;
        this.state = state;
        this.city = city;
        this.street = street;
        this.addressNumber = addressNumber;
        this.floor = floor;
        this.departmentNumber = departmentNumber;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getContactInfoId() {
        return contactInfoId;
    }

    public void setContactInfoId(Long contactInfoId) {
        this.contactInfoId = contactInfoId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContactInfo that = (ContactInfo) o;
        return contactInfoId.equals(that.contactInfoId) && userId.equals(that.userId) && state.equals(that.state) && city.equals(that.city) && street.equals(that.street) && addressNumber.equals(that.addressNumber) && Objects.equals(floor, that.floor) && Objects.equals(departmentNumber, that.departmentNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(contactInfoId, userId, state, city, street, addressNumber, floor, departmentNumber);
    }
}
