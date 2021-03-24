package ar.edu.itba.paw.models;

public class Job {
    private String description;
    private int averageRating, serviceType;
    private Number id;
    private User provider;

    public Job(String description, int averageRating, int serviceType, Number id, User provider) {
        this.description = description;
        this.averageRating = averageRating;
        this.serviceType = serviceType;
        this.id = id;
        this.provider = provider;
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
        return provider;
    }

    public void setProviderId(User provider) {
        this.provider = provider;
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

    @Override
    public String toString() {
        return "Job{" +
                "description='" + description + '\'' +
                ", averageRating=" + averageRating +
                ", serviceType=" + serviceType +
                ", id=" + id +
                ", provider=" + provider +
                '}';
    }
}
