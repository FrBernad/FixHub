package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistance.LocationDao;
import ar.edu.itba.paw.models.City;
import ar.edu.itba.paw.models.State;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Repository
public class LocationDaoImpl implements LocationDao {

    @PersistenceContext
    private EntityManager em;

    private static final Logger LOGGER = LoggerFactory.getLogger(LocationDaoImpl.class);


    @Override
    public Collection<State> getStates() {

        LOGGER.debug("Getting all states");
        return em.createQuery("FROM State", State.class).getResultList();
    }

    @Override
    public Optional<State> getStateById(long stateId) {
        LOGGER.debug("Getting state with id {}", stateId);
        return Optional.ofNullable(em.find(State.class, stateId));
    }

    @Override
    public Collection<City> getCitiesByState(State state) {
        LOGGER.debug("Getting cities of the state  {}", state.getName());
        return em
            .createQuery("FROM City WHERE state = :state", City.class)
            .setParameter("state", state)
            .getResultList();
    }

    @Override
    public Collection<City> getCitiesById(Collection<Long> citiesIds) {
        return em
            .createQuery("FROM City WHERE id IN :cities",City.class)
            .setParameter("cities",citiesIds)
            .getResultList();
    }

    @Override
    public Optional<City> getCityByCityIdAndState(long cityId, State state) {
        LOGGER.debug("Getting city with id {} of the state  {}", cityId,state.getName());

        return em
            .createQuery("FROM City WHERE id = :cityId AND state = :state", City.class)
            .setParameter("state", state)
            .setParameter("cityId", cityId)
            .getResultList()
            .stream()
            .findFirst();
    }
}
