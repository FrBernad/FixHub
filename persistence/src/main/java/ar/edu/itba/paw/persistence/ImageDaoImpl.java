package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.mail.Multipart;
import javax.sql.DataSource;

@Repository
public class ImageDaoImpl {
    @Autowired
    private DataSource ds;

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;

    private static final RowMapper<Image> IMAGE_ROW_MAPPER = (rs, rowNum) ->
        new Image(rs.getLong("imageId"),
                  rs.getBytes("data"));

    @Autowired
    public ImageDaoImpl(final DataSource ds){
        jdbcTemplate = new JdbcTemplate(ds);
        simpleJdbcInsert = new SimpleJdbcInsert(ds).withTableName("IMAGES").usingGeneratedKeyColumns("i_id");
    }

    @Override
    public Image createImage(MultipartFile file){

    }


}
