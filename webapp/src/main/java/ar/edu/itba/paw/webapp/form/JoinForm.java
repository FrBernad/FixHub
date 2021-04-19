package ar.edu.itba.paw.webapp.form;


import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Min;
import java.util.List;

public class JoinForm {

    @Min(1)
    private long state;

    private List<Long> city;

    @NotEmpty
    private String startTime;

    @NotEmpty
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
