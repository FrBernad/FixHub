package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.models.JobCategory;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import java.math.BigDecimal;

public class ServiceForm {

    @NotBlank
    private String jobProvided;

    @NotBlank
    private String description;

    //TODO: Validar que esté vacío. NotBlank no funciona con números.
    @Range(min = 1, max = 999999)
    private BigDecimal price;

    private JobCategory jobCategory;

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

    public JobCategory getJobCategory() {return jobCategory;}

    public void setJobCategory(JobCategory jobCategory) {this.jobCategory = jobCategory;}

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
