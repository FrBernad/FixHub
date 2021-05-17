package ar.edu.itba.paw.models.user;

import ar.edu.itba.paw.models.ContactInfo;
import ar.edu.itba.paw.models.Image;
import ar.edu.itba.paw.models.job.JobContact;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "users")
@DiscriminatorColumn(name = "user_type")
public abstract class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_u_id_seq")
    @SequenceGenerator(sequenceName = "users_u_id_seq", name = "users_u_id_seq", allocationSize = 1)
    @Column(name = "u_id")
    private Long id;

    @Column(name = "u_password", length = 128, nullable = false)
    private String password;

    @Column(name = "u_name", length = 50, nullable = false)
    private String name;

    @Column(name = "u_surname", length = 50, nullable = false)
    private String surname;

    @Column(name = "u_email", length = 200, nullable = false, unique = true)
    private String email;

    @Column(name = "u_phone_number", length = 15, nullable = false)
    private String phoneNumber;

    @OneToOne
    @JoinColumn(name = "u_profile_picture")
    private Image profileImage;

    @OneToOne
    @JoinColumn(name = "u_cover_picture")
    private Image coverImage;

    @Column(name = "u_state", length = 50, nullable = false)
    private String state;

    @Column(name = "u_city", length = 50, nullable = false)
    private String city;

    @ElementCollection(targetClass = Roles.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "roles", joinColumns = @JoinColumn(name = "r_user_id"))
    @Column(name = "r_role", nullable = false)
    @Enumerated(EnumType.STRING)
    private Set<Roles> roles;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "follows",
        joinColumns = @JoinColumn(name = "f_user_id"),
        inverseJoinColumns = @JoinColumn(name = "f_followed_user_id")
    )
    private Set<User> following;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "following")
    private Set<User> followers;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Set<ContactInfo> contactInfo;

    @OneToMany(mappedBy = "user")
    private Set<JobContact> providersContacted;


    /* default */
    protected User() {
        // Just for Hibernate
    }

    public User(final String password,
                final String name,
                final String surname,
                final String email,
                final String phoneNumber,
                final String state,
                final String city,
                final Set<Roles> roles) {
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.state = state;
        this.city = city;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Image getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(Image profileImage) {
        this.profileImage = profileImage;
    }

    public Image getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(Image coverImage) {
        this.coverImage = coverImage;
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

    public boolean hasRole(String role) {
        return roles.stream().anyMatch(p -> p.name().equals(role));
    }

    public Set<Roles> getRoles() {
        return roles;
    }

    public void setRoles(Set<Roles> roles) {
        this.roles = roles;
    }

    public void addRole(Roles role) {
        roles.add(role);
    }

    public void removeRole(Roles role) {
        roles.remove(role);
    }

    public boolean getIsProvider() {
        return roles.stream().anyMatch(p -> p.name().equals(Roles.PROVIDER.name()));
    }

    public Set<User> getFollowing() {
        return following;
    }

    public void setFollowing(Set<User> following) {
        this.following = following;
    }

    public Set<User> getFollowers() {
        return followers;
    }

    public void setFollowers(Set<User> followers) {
        this.followers = followers;
    }

    public Set<ContactInfo> getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(Set<ContactInfo> contactInfo) {
        this.contactInfo = contactInfo;
    }

    public Set<JobContact> getProvidersContacted() {
        return providersContacted;
    }

    public void setProvidersContacted(Set<JobContact> providersContacted) {
        this.providersContacted = providersContacted;
    }

    public ContactInfo getContactInfoById(Long id) {
        for (ContactInfo contactInfo : this.contactInfo) {
            if(contactInfo.getContactInfoId().equals(id)) {
                return contactInfo;
            }
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "User{" +
            "id=" + id +
            ", password='" + password + '\'' +
            ", name='" + name + '\'' +
            ", surname='" + surname + '\'' +
            ", email='" + email + '\'' +
            ", phoneNumber='" + phoneNumber + '\'' +
            ", profileImage=" + profileImage +
            ", coverImage=" + coverImage +
            ", state='" + state + '\'' +
            ", city='" + city + '\'' +
            ", roles=" + roles +
            ", following=" + following +
            ", followers=" + followers +
            ", contactInfo=" + contactInfo +
            ", providersContacted=" + providersContacted +
            '}';
    }
}
