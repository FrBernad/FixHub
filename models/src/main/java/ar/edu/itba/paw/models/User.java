package ar.edu.itba.paw.models;

public class User {
    private String name, password;
    private Number id;

    public User(Number id, String name, String password) {
        this.name = name;
        this.id = id;
        this.password = password;
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

    public Number getId() {
        return id;
    }

    public void setId(Number id) {
        this.id = id;
    }
}
