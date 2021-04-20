package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistance.LocationDao;
import ar.edu.itba.paw.models.City;
import ar.edu.itba.paw.models.State;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Repository
public class LocationDaoImpl implements LocationDao {

    @Autowired
    private DataSource ds;

    private JdbcTemplate jdbcTemplate;

    private static final ResultSetExtractor<Collection<State>> STATE_ROW_MAPPER = rs -> {
        List<State> states = new LinkedList<>();
        while(rs.next()) {
            states.add(new State(rs.getLong("s_id"), rs.getString("s_name")));
        }
        return states;
    };

    private static final ResultSetExtractor<Collection<City>> CITY_ROW_MAPPER = rs -> {
        List<City> cities = new LinkedList<>();
        while(rs.next()) {
            cities.add(new City(rs.getLong("c_id"), rs.getString("c_name")));
        }
        return cities;
    };



    @Autowired
    public LocationDaoImpl(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
    }


    @Override
    public Collection<State> getStates() {
        return jdbcTemplate.query("SELECT * FROM STATES", STATE_ROW_MAPPER);
    }

    @Override
    public Optional<State> getStateById(long stateId) {
        return jdbcTemplate.query("SELECT * FROM STATES WHERE s_id = ?", new Object[]{stateId}, STATE_ROW_MAPPER).stream().findFirst();
    }

    @Override
    public Collection<City> getCitiesByStateId(long stateId) {
        return jdbcTemplate.query("SELECT * FROM CITIES WHERE c_state_id = ?", new Object[]{stateId}, CITY_ROW_MAPPER);
    }
}