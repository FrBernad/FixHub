package ar.edu.itba.paw.models;

import java.util.Collection;

public class ProviderLocation {
    private Long providerId;
    private Collection<City> cities;
    private String state;

    public ProviderLocation(Long providerId, Collection<City> cities, String state) {
        this.providerId = providerId;
        this.cities = cities;
        this.state = state;
    }

    public Long getProviderId() {
        return providerId;
    }

    public void setProviderId(Long providerId) {
        this.providerId = providerId;
    }

    public Collection<City> getCities() {
        return cities;
    }

    public void setCities(Collection<City> cities) {
        this.cities = cities;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
