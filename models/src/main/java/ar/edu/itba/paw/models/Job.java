package ar.edu.itba.paw.models;

public class Job {
    private String description;
    private int averageRating, serviceType;
    private Number id;
    private User providerId;

    public Job(String description, int averageRating, int serviceType, Number id, User providerId) {
        this.description = description;
        this.averageRating = averageRating;
        this.serviceType = serviceType;
        this.id = id;
        this.providerId = providerId;
    }

    public String getDescription() {
        return description;
    }

    public int getAverageRating() {
        return averageRating;
    }

    public int getServiceType() {
        return serviceType;
    }

    public Number getId() {
        return id;
    }

    public User getProviderId() {
        return providerId;
    }

    public void setProviderId(User providerId) {
        this.providerId = providerId;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAverageRating(int averageRating) {
        this.averageRating = averageRating;
    }

    public void setServiceType(int serviceType) {
        this.serviceType = serviceType;
    }

    public void setId(long id) {
        this.id = id;
    }

}
