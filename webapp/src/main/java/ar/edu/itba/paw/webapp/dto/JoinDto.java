package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.webapp.dto.customValidations.FieldsValueNotMatch;

import javax.validation.constraints.Min;

@FieldsValueNotMatch(
    field = "startTime",
    fieldNotMatch = "endTime"
)
public class JoinDto {

    private ScheduleDto schedule;

    private LocationDto location;

    public ScheduleDto getSchedule() {
        return schedule;
    }

    public void setSchedule(ScheduleDto schedule) {
        this.schedule = schedule;
    }

    public LocationDto getLocation() {
        return location;
    }

    public void setLocation(LocationDto location) {
        this.location = location;
    }
}

