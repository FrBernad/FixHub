package ar.edu.itba.paw.interfaces.persistance;

import ar.edu.itba.paw.models.City;
import ar.edu.itba.paw.models.State;

import java.util.Collection;
import java.util.Optional;

public interface LocationDao {

    Collection<State> getStates();

    Optional<State> getStateById(long stateId);

    Collection<City> getCitiesByState(State state);

    Collection<City> getCitiesById(Collection<Long> citiesIds);

    Optional<City> getCityByCityIdAndState(long cityId, State state);
}
