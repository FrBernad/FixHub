package ar.edu.itba.paw.webapp.dto.request;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class NewReviewDto {

    @NotEmpty
    @Size(min=4,max=300)
    private String description;

    @NotEmpty
    @Pattern(regexp = "[1-5]")
    private String rating;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }


}
