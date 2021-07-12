package ar.edu.itba.paw.models.contact;

import ar.edu.itba.paw.models.job.Job;
import ar.edu.itba.paw.models.user.User;

public class NewContactDto {

    private Job job;
    private Long contactInfoId;
    private User user;
    private String message, state, city, street, addressNumber, floor, departmentNumber;

    public NewContactDto(Job job, Long contactInfoId, User user, String message, String state, String city, String street, String addressNumber, String floor, String departmentNumber) {
        this.job = job;
        this.contactInfoId = contactInfoId;
        this.user = user;
        this.message = message;
        this.state = state;
        this.city = city;
        this.street = street;
        this.addressNumber = addressNumber;
        this.floor = floor;
        this.departmentNumber = departmentNumber;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public Long getContactInfoId() {
        return contactInfoId;
    }

    public void setContactInfoId(Long contactInfoId) {
        this.contactInfoId = contactInfoId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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
}
