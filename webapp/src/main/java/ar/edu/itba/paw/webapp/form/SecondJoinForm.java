package ar.edu.itba.paw.webapp.form;


import ar.edu.itba.paw.webapp.form.customValidations.FieldsValueNotMatch;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Min;
import java.util.List;
@FieldsValueNotMatch(
    field = "startTime",
    fieldNotMatch = "endTime"
)
public class SecondJoinForm {

    @Min(1)
    private long state;

    @NotEmpty
    private List<Long> city;

    private String startTime;

    private String endTime;

    public long getState() {
        return state;
    }

    public void setState(long state) {
        this.state = state;
    }

    public List<Long> getCity() {
        return city;
    }

    public void setCity(List<Long> city) {
        this.city = city;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
