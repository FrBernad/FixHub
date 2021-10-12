package ar.edu.itba.paw.webapp.dto.response;

import ar.edu.itba.paw.models.job.Job;

import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "")
public class SingleJobDto extends JobDto {

    private boolean canReview;

    public SingleJobDto() {
        //used by Jersey
    }

    public SingleJobDto(Job job, UriInfo uriInfo, boolean canReview) {
        super(job, uriInfo);
        this.canReview = canReview;
    }

    public boolean isCanReview() {
        return canReview;
    }

    public void setCanReview(boolean canReview) {
        this.canReview = canReview;
    }
}
