package ar.edu.itba.paw.models;

import java.util.Collection;

public class State {

    private long id;
    private String name;
    private Collection<City> cities;


    public State(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public State(long id, String name, Collection<City> cities) {
        this.id = id;
        this.name = name;
        this.cities = cities;
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

    public Collection<City> getCities() {
        return cities;
    }

    public void setCities(Collection<City> cities) {
        this.cities = cities;
    }

}
