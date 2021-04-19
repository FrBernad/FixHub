package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.persistance.LocationDao;
import ar.edu.itba.paw.interfaces.services.LocationService;
import ar.edu.itba.paw.models.City;
import ar.edu.itba.paw.models.State;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.Optional;

@org.springframework.stereotype.Service
public class LocationServiceImpl implements LocationService {

    @Autowired
    private LocationDao locationDao;


    @Override
    public Collection<State> getStates(){
        return locationDao.getStates();
    };

    @Override
    public Optional<State> getStateById(long stateId) {
        return locationDao.getStateById(stateId);
    }

    @Override
    public Collection<City> getCitiesByStateId(long stateId) {
        return locationDao.getCitiesByStateId(stateId);
    }
}
