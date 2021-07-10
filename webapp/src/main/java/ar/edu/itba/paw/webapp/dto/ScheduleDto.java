package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.user.provider.Schedule;

public class ScheduleDto {

    private String startTime;
    private String endTime;

    public ScheduleDto(){

    }

    public ScheduleDto(Schedule schedule) {
        this.startTime = schedule.getStartTime();
        this.endTime = schedule.getEndTime();
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
