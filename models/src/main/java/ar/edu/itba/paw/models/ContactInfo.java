package ar.edu.itba.paw.models;

import ar.edu.itba.paw.models.job.JobContact;
import ar.edu.itba.paw.models.user.User;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "contact_info")
public class ContactInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "contact_info_id_seq")
    @SequenceGenerator(sequenceName = "contact_info_id_seq", name = "contact_info_id_seq", allocationSize = 1)
    @Column(name = "ci_id",nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="ci_user_id")
    private User user;

    @Column(name = "ci_state",length = 50,nullable = false)
    private String state;

    @Column(name = "ci_city",length = 50,nullable = false)
    private String city;

    @Column(name = "ci_street",length = 50,nullable = false)
    private String street;

    @Column(name = "ci_address_number",length = 50,nullable = false)
    private String addressNumber;

    @Column(name = "ci_floor",length = 5)
    private String floor;

    @Column(name = "ci_department_number",length = 50)
    private String departmentNumber;

    @OneToMany(mappedBy = "contactInfo", fetch = FetchType.LAZY)
    private Set<JobContact> jobContact;

    protected ContactInfo() {

    }

    public ContactInfo(User user, String state, String city, String street, String addressNumber, String floor, String departmentNumber) {
        this.user = user;
        this.state = state;
        this.city = city;
        this.street = street;
        this.addressNumber = addressNumber;
        this.floor = floor;
        this.departmentNumber = departmentNumber;
    }



    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getContactInfoId() {
        return id;
    }

    public void setContactInfoId(Long id) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ContactInfo)) return false;
        ContactInfo that = (ContactInfo) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
