package ar.edu.itba.paw.webapp.dto.request;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class NewReviewDto {

    @NotEmpty(message = "{NotEmpty.newReviewDto.description}")
    @Size(min=4,max=300, message="{Size.newReviewDto.description}")
    private String description;

    @NotEmpty(message = "{NotEmpty.newReviewDto.rating}")
    @Pattern(regexp = "[1-5]", message = "{Pattern.newReviewDto.rating}")
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
