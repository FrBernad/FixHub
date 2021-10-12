package ar.edu.itba.paw.webapp.dto.response;

import ar.edu.itba.paw.models.location.City;

import javax.xml.bind.annotation.XmlType;
import java.util.Collection;
import java.util.stream.Collectors;

@XmlType(name = "")
public class CityDto {

    public static Collection<CityDto> mapCityToDto(Collection<City> states) {
        return states.stream().map(CityDto::new).collect(Collectors.toList());
    }

    private long id;
    private String name;

    public CityDto() {
    }

    public CityDto(City city) {
        this.id = city.getId();
        this.name = city.getName();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
