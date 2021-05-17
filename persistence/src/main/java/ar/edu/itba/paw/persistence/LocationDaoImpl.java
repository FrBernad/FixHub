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

    @Override
    public Collection<State> getStates() {
        return em.createQuery("FROM State", State.class).getResultList();
    }

    @Override
    public Optional<State> getStateById(long stateId) {
        return Optional.ofNullable(em.find(State.class, stateId));
    }

    @Override
    public Collection<City> getCitiesByState(State state) {
        return em
            .createQuery("FROM City WHERE state = :state", City.class)
            .setParameter("state", state)
            .getResultList();
    }

    @Override
    public Optional<City> getCityByCityAndStateId(long cityId, State state) {
        return em
            .createQuery("FROM City WHERE id = :cityId AND state = :state", City.class)
            .setParameter("state", state)
            .setParameter("cityId", cityId)
            .getResultList()
            .stream()
            .findFirst();
    }

//    private final JdbcTemplate jdbcTemplate;
//
//    private static final ResultSetExtractor<Collection<State>> STATE_ROW_MAPPER = rs -> {
//        List<State> states = new LinkedList<>();
//        while (rs.next()) {
//            states.add(new State(rs.getLong("s_id"), rs.getString("s_name")));
//        }
//        return states;
//    };
//
//    private static final ResultSetExtractor<Collection<City>> CITY_ROW_MAPPER = rs -> {
//        List<City> cities = new LinkedList<>();
//        while (rs.next()) {
//            cities.add(new City(rs.getLong("c_id"), rs.getString("c_name")));
//        }
//        return cities;
//    };
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(LocationDaoImpl.class);
//
//
//    @Autowired
//    public LocationDaoImpl(final DataSource ds) {
//        jdbcTemplate = new JdbcTemplate(ds);
//    }
//
//
//    @Override
//    public Collection<State> getStates() {
//        final String query = "SELECT * FROM STATES";
//        LOGGER.debug("Executing query: {}", query);
//        return jdbcTemplate.query(query, STATE_ROW_MAPPER);
//    }
//
//    @Override
//    public Optional<State> getStateById(long stateId) {
//        final String query = "SELECT * FROM STATES WHERE s_id = ?";
//        LOGGER.debug("Executing query: {}", query);
//        return jdbcTemplate.query(query, new Object[]{stateId}, STATE_ROW_MAPPER).stream().findFirst();
//    }
//
//    @Override
//    public Collection<City> getCitiesByStateId(long stateId) {
//        final String query = "SELECT * FROM CITIES WHERE c_state_id = ?";
//        LOGGER.debug("Executing query: {}", query);
//        return jdbcTemplate.query(query, new Object[]{stateId}, CITY_ROW_MAPPER);
//    }
//
//    @Override
//    public Optional<City> getCityByCityAndStateId(long cityId, long stateId) {
//        final String query = "SELECT * FROM CITIES WHERE c_id = ? AND c_state_id = ?";
//        LOGGER.debug("Executing query: {}", query);
//        return jdbcTemplate.query(query, new Object[]{cityId, stateId}, CITY_ROW_MAPPER).stream().findFirst();
//    }
}
