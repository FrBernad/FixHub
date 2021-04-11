package ar.edu.itba.paw.models;

import java.util.Collection;

public class User {
    private String name, password,surname,city,state,phoneNumber,email;
    private Number id;
    private Collection<UserRoles> roles;

    /*FIXME: sacar el constructor  de user default cuando haya autenticación*/
    public User() {

    }

    public User(Number id, String password, String name, String surname, String email, String phoneNumber, String state, String city, Collection<UserRoles> roles) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.state = state;
        this.city = city;
        this.roles = roles;
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

    public Number getId() {
        return id;
    }

    public void setId(Number id) {
        this.id = id;
    }

    public Collection<UserRoles> getRoles() {
        return roles;
    }

    public void setRoles(Collection<UserRoles> roles) {
        this.roles = roles;
    }

    public void addRole(UserRoles role){
        roles.add(role);
    }
}
