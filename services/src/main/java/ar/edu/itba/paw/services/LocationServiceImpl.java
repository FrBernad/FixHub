package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.persistance.LocationDao;
import ar.edu.itba.paw.interfaces.services.LocationService;
import ar.edu.itba.paw.models.location.City;
import ar.edu.itba.paw.models.location.State;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.Optional;

@org.springframework.stereotype.Service
public class LocationServiceImpl implements LocationService {

    @Autowired
    private LocationDao locationDao;

    private static final Logger LOGGER = LoggerFactory.getLogger(LocationServiceImpl.class);

    @Override
    public Collection<State> getStates() {
        LOGGER.debug("Retrieving states");
        return locationDao.getStates();
    }

    @Override
    public Optional<State> getStateById(long stateId) {
        LOGGER.debug("Retrieving state with id {}", stateId);
        return locationDao.getStateById(stateId);
    }

    @Override
    public Collection<City> getCitiesByState(State state) {
        LOGGER.debug("Retrieving cities with state id {}", state);
        return locationDao.getCitiesByState(state);
    }
}
