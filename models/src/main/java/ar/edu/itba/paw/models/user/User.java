package ar.edu.itba.paw.models.user;

import ar.edu.itba.paw.models.Image;

import javax.persistence.*;
import java.util.Collection;
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
    private Collection<Roles> roles;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "follows",
        joinColumns = @JoinColumn(name = "f_user_id"),
        inverseJoinColumns = @JoinColumn(name = "f_followed_user_id")
    )
    private Set<User> following;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "following")
    private Set<User> followers;

    /* default */
    protected User() {
        // Just for Hibernate
    }

    public User(String password, String name, String surname, String email, String phoneNumber, String state, String city, Collection<Roles> roles, Image profileImage, Image coverImage) {
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.state = state;
        this.city = city;
        this.roles = roles;
        this.coverImage = coverImage;
        this.profileImage = profileImage;
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

    public boolean hasRole(String role) {
        return roles.stream().anyMatch(p -> p.name().equals(role));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Collection<Roles> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Roles> roles) {
        this.roles = roles;
    }

    public void addRole(Roles role) {
        roles.add(role);
    }

    public void removeRole(Roles role) {
        roles.remove(role);
    }

    public Set<User> getFollowers() {
        return followers;
    }

    public void setFollowers(Set<User> followers) {
        this.followers = followers;
    }

    public Set<User> getFollowing() {
        return following;
    }

    public void setFollowing(Set<User> following) {
        this.following = following;
    }

    public boolean getIsProvider() {
        return roles.stream().anyMatch(p -> p.name().equals(Roles.PROVIDER.name()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return name.equals(user.name) && password.equals(user.password) && surname.equals(user.surname) && city.equals(user.city) && state.equals(user.state) && phoneNumber.equals(user.phoneNumber) && email.equals(user.email) && id.equals(user.id) && Objects.equals(profileImage, user.profileImage) && Objects.equals(coverImage, user.coverImage) && roles.containsAll(user.roles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, id);
    }
}
