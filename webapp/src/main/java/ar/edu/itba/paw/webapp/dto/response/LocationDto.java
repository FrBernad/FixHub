package ar.edu.itba.paw.webapp.dto.response;

import ar.edu.itba.paw.models.location.City;
import ar.edu.itba.paw.models.user.provider.Location;

import javax.ws.rs.core.UriInfo;
import javax.xml.bind.annotation.XmlType;
import java.util.Collection;
import java.util.LinkedList;

@XmlType(name = "")
public class LocationDto {

    private StateDto state;
    private Collection<CityDto> cities;

    public LocationDto(){

    }

    public LocationDto(Location location, UriInfo uriInfo) {
        this.state = new StateDto(location.getState(), uriInfo);
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
