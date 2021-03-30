package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.models.JobCategories;

public class ServiceForm {

    private String jobProvided,description;
    private JobCategories jobCategory;

    public String getJobProvided() {
        return jobProvided;
    }

    public void setJobProvided(String jobProvided) {
        this.jobProvided = jobProvided;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public JobCategories getJobCategory() {
        return jobCategory;
    }

    public void setJobCategory(JobCategories jobCategory) {
        this.jobCategory = jobCategory;
    }
}
