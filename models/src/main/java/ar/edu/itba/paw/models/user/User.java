package ar.edu.itba.paw.models.user;

import ar.edu.itba.paw.models.contact.ContactInfo;
import ar.edu.itba.paw.models.image.Image;
import ar.edu.itba.paw.models.user.provider.ProviderDetails;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

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

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
    private Set<ContactInfo> contactInfo;

    @Embedded
    private ProviderDetails providerDetails;

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

    public boolean hasRole(Roles role) {
        return roles.stream().anyMatch(p -> p.equals(role));
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

    public boolean isProvider() {
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

    public ProviderDetails getProviderDetails() {
        return providerDetails;
    }

    public void setProviderDetails(ProviderDetails providerDetails) {
        this.providerDetails = providerDetails;
    }

    public boolean userIsFollowing(String email) {
        return following.stream().anyMatch(user -> user.getEmail().equals(email));
    }

    public boolean userIsFollower(String email) {
        return followers.stream().anyMatch(user -> user.getEmail().equals(email));
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


}
