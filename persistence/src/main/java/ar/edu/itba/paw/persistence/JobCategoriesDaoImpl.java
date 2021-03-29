package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.JobCategoriesDao;
import ar.edu.itba.paw.models.JobCategories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Collection;

@Repository
public class JobCategoriesDaoImpl implements JobCategoriesDao{

    @Autowired
    private DataSource ds;

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;

    private static final RowMapper<JobCategories> JOB_CATEGORIES_ROW_MAPPER = (rs,rowNum) ->
            new JobCategories(rs.getLong("id"),rs.getString("name"));

    @Autowired
    public JobCategoriesDaoImpl(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        simpleJdbcInsert = new SimpleJdbcInsert(ds).withTableName("JOBSCATEGORIES").usingGeneratedKeyColumns("id");
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS " +
                "JOBCATEGORIES(" +
                "id SERIAL," +
                "name TEXT UNIQUE,"+
                "PRIMARY KEY(id))");
    }

    @Override
    public Collection<JobCategories> getJobs() {
        return jdbcTemplate.query("SELECT * FROM JOBCATEGORIES",JOB_CATEGORIES_ROW_MAPPER);
    }
}
