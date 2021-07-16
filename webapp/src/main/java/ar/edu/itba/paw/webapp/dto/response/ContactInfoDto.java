package ar.edu.itba.paw.webapp.dto.response;


import ar.edu.itba.paw.models.contact.ContactInfo;

import javax.xml.bind.annotation.XmlType;
import java.util.Collection;
import java.util.stream.Collectors;


@XmlType(name="")
public class ContactInfoDto {

    public static Collection<ContactInfoDto> mapCollectionInfoToDto(Collection<ContactInfo> contactInfoCollection) {
        return contactInfoCollection.stream().map(ContactInfoDto::new).collect(Collectors.toSet());
    }

    private Long id;

    private String state;

    private String city;

    private String street;

    private String addressNumber;

    private String floor;

    private String departmentNumber;

    public ContactInfoDto() {
        // Used by Jersey
    }

    public ContactInfoDto(ContactInfo contactInfo) {
        this.id = contactInfo.getId();
        this.state = contactInfo.getState();
        this.city = contactInfo.getCity();
        this.street = contactInfo.getStreet();
        this.addressNumber = contactInfo.getAddressNumber();
        this.floor = contactInfo.getFloor();
        this.departmentNumber = contactInfo.getDepartmentNumber();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
