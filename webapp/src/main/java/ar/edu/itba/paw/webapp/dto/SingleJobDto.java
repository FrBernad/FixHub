package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.job.Job;

import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;

public class SingleJobDto extends JobDto{

    private boolean canReview;

    public SingleJobDto() {
       //used by Jersey
    }

    public SingleJobDto(Job job, UriInfo uriInfo, SecurityContext securityContext,boolean canReview) {
        super(job, uriInfo, securityContext);
        this.canReview=canReview;
    }

    public boolean isCanReview() {
        return canReview;
    }

    public void setCanReview(boolean canReview) {
        this.canReview = canReview;
    }
}
