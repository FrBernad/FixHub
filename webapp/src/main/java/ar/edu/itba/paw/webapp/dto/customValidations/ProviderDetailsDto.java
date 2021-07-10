package ar.edu.itba.paw.webapp.dto.customValidations;

import ar.edu.itba.paw.models.user.provider.ProviderDetails;
import ar.edu.itba.paw.webapp.dto.LocationDto;
import ar.edu.itba.paw.webapp.dto.ScheduleDto;

public class ProviderDetailsDto {

    private LocationDto location;
    private ScheduleDto schedule;

    public ProviderDetailsDto(){

    }

    public ProviderDetailsDto(ProviderDetails providerDetails){
        this.location = new LocationDto(providerDetails.getLocation());
        this.schedule = new ScheduleDto(providerDetails.getSchedule());
    }

    public LocationDto getLocation() {
        return location;
    }

    public void setLocation(LocationDto location) {
        this.location = location;
    }

    public ScheduleDto getSchedule() {
        return schedule;
    }

    public void setSchedule(ScheduleDto schedule) {
        this.schedule = schedule;
    }
}


