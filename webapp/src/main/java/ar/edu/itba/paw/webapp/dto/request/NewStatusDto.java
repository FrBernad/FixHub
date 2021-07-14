package ar.edu.itba.paw.webapp.dto.request;

import ar.edu.itba.paw.models.job.JobStatus;
import org.hibernate.validator.constraints.NotEmpty;

public class NewStatusDto {

    @NotEmpty
    JobStatus status;

    public JobStatus getStatus() {
        return status;
    }

    public void setStatus(JobStatus status) {
        this.status = status;
    }
}
