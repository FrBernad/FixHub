package ar.edu.itba.paw.models;


public class Job {
    private String description,jobProvided;
    private int averageRating;
    private Number jobType;
    private Number id;
    private User provider;

    public Job(String description,String jobProvided, int averageRating, Number jobType, Number id, User provider) {
        this.description = description;
        this.jobProvided = jobProvided;
        this.averageRating = averageRating;
        this.jobType = jobType;
        this.id = id;
        this.provider = provider;
    }

    @Override
    public String toString() {
        return "Job{" +
                "description='" + description + '\'' +
                ", jobProvided='" + jobProvided + '\'' +
                ", averageRating=" + averageRating +
                ", jobType=" + jobType +
                ", id=" + id +
                ", provider=" + provider +
                '}';
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getJobProvided() {
        return jobProvided;
    }

    public void setJobProvided(String jobProvided) {
        this.jobProvided = jobProvided;
    }

    public int getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(int averageRating) {
        this.averageRating = averageRating;
    }

    public Number getJobType() {
        return jobType;
    }

    public void setJobType(Number jobType) {
        this.jobType = jobType;
    }

    public Number getId() {
        return id;
    }

    public void setId(Number id) {
        this.id = id;
    }

    public User getProvider() {
        return provider;
    }

    public void setProvider(User provider) {
        this.provider = provider;
    }
}
