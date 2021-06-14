package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.location.City;
import ar.edu.itba.paw.models.location.State;

import java.util.Collection;
import java.util.Optional;

public interface LocationService {

    Collection<State> getStates();

    Optional<State> getStateById(long stateId);

    Collection<City> getCitiesByState(State state);
}
