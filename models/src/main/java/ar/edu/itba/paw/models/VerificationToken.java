package ar.edu.itba.paw.models;

public class VerificationToken {

   private String value;
   private long id;
   private User user;

    public VerificationToken(String value, long id, User user) {
        this.value = value;
        this.id = id;
        this.user = user;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
