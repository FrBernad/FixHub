package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.location.City;
import ar.edu.itba.paw.models.user.provider.Location;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class LocationDto {

    private StateDto state;
    private Collection<CityDto> cities;

    public LocationDto(){

    }

    public LocationDto(Location location) {
        this.state = new StateDto(location.getState());
        this.cities = new LinkedList<>();
        for (City city : location.getCities()){
            cities.add(new CityDto(city));
        }
    }

    public StateDto getState() {
        return state;
    }

    public void setState(StateDto state) {
        this.state = state;
    }

    public Collection<CityDto> getCities() {
        return cities;
    }

    public void setCities(Collection<CityDto> cities) {
        this.cities = cities;
    }
}
