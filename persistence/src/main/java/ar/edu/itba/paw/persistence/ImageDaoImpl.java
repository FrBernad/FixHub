package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistance.ImageDao;
import ar.edu.itba.paw.models.Image;
import ar.edu.itba.paw.models.ImageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.*;

@Repository
public class ImageDaoImpl implements ImageDao {
    @Autowired
    private DataSource ds;

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert imageSimpleJdbcInsert;

    private static final RowMapper<Image> IMAGE_ROW_MAPPERS = (rs, rowNum) ->
        new Image(rs.getLong("i_id"), rs.getBytes("i_data"), rs.getString("i_mime_type"));


    @Autowired
    public ImageDaoImpl(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        imageSimpleJdbcInsert = new SimpleJdbcInsert(ds).withTableName("IMAGES").usingGeneratedKeyColumns("i_id");
    }


    public List<Image> createImages(List<ImageDto> imageInfo) {
        List<Image> images = new LinkedList<>();
        for (ImageDto image : imageInfo) {
            images.add(createImage(image));
        }
        return images;
    }

    public Image createImage(ImageDto image) {
        Map<String, Object> imageInfo = new HashMap<>();
        imageInfo.put("i_data", image.getData());
        imageInfo.put("i_mime_type", image.getMimeType());
        final Number id = imageSimpleJdbcInsert.executeAndReturnKey(imageInfo);
        return new Image(id.longValue(), image.getData(), image.getMimeType());
    }

    @Override
    public Optional<Image> getImageById(Long imageId) {
        return jdbcTemplate.query("SELECT * FROM IMAGES where i_id = ?", new Object[]{imageId}, IMAGE_ROW_MAPPERS).stream().findFirst();
    }


}


