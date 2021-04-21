package ar.edu.itba.paw.webapp.form;

import javax.validation.constraints.Min;

public class FirstJoinForm {

    @Min(1)
    private long state;

    private String startTime;

    private String endTime;

    public long getState() {
        return state;
    }

    public void setState(long state) {
        this.state = state;
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

