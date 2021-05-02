package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistance.ImageDao;
import ar.edu.itba.paw.models.Image;
import ar.edu.itba.paw.models.ImageDto;
import ar.edu.itba.paw.models.JobCategory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.*;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

@Rollback
//@Sql(scripts = "classpath:image-dao-test.sql")
@ContextConfiguration(classes = TestConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class ImageDaoTest {

    @Autowired
    private DataSource ds;

    @Autowired
    private ImageDao imageDao;

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert jobJdbcInsert;
    private SimpleJdbcInsert jobImageJdbcInsert;


    //User data
    private static final Integer USER_ID=1;

    //Job data
    private static final String JOB_PROVIDED = "Arreglo de mesas y sillas";
    private static final JobCategory JOB_CATEGORY = JobCategory.CARPINTERO;
    private static final String JOB_DESCRIPTION = "Trabajo en muebles de roble y pino";
    private static final BigDecimal JOB_PRICE = BigDecimal.valueOf(1500);
    private static final Integer JOB_ID=1;

    private final byte[] imgInfo1={0,4,2,7,8,3,5,8,9,5,4,2};
    private final byte[] imgInfo2={1,2,4,9,8,0,3,7,4,2,1,1};
    private final byte[] imgInfo3={2,2,1,3,0,4,9,4,9,9,1,9};

    private final String imgType =".png";

    @Before
    public void setUp(){

        jdbcTemplate=new JdbcTemplate(ds);
        jobJdbcInsert =new SimpleJdbcInsert(ds).withTableName("jobs").usingGeneratedKeyColumns("j_id");
        jobImageJdbcInsert=new SimpleJdbcInsert(ds).withTableName("job_image");
    }

    @Test
    public void createImageTest() {

        final ImageDto imgDto = new ImageDto(imgInfo1, imgType);
        final Image img = imageDao.createImage(imgDto);

        assertNotNull(img);
        assertEquals(imgInfo1,img.getData());
        assertEquals(imgType,img.getMimeType());

    }

    @Test
    public void createImagesTest(){

        List<ImageDto> dtos1 = new ArrayList<>();

        dtos1.add(new ImageDto(imgInfo1,imgType));
        dtos1.add(new ImageDto(imgInfo2,imgType));
        dtos1.add(new ImageDto(imgInfo2,imgType));

        List<Image> dtos2 = imageDao.createImages(dtos1);

        assertNotNull(dtos2);

        assertEquals(dtos1.size(), JdbcTestUtils.countRowsInTableWhere(jdbcTemplate,"images",""));

        for (int i = 0; i < dtos1.size() ; i++) {
            assertArrayEquals(dtos1.get(i).getData(),dtos2.get(i).getData());
            assertEquals(dtos1.get(i).getMimeType(),dtos2.get(i).getMimeType());
        }

    }


    @Test
    public void getImageByIdTest(){

        Image imgIn =imageDao.createImage(new ImageDto(imgInfo1,imgType));

        Optional<Image> imgOutOptl = imageDao.getImageById(imgIn.getImageId());

        assertNotNull(imgOutOptl);
        assertTrue(imgOutOptl.isPresent());
        assertEquals(imgIn.getImageId(),imgOutOptl.get().getImageId());
        assertArrayEquals(imgIn.getData(), imgOutOptl.get().getData());
        assertEquals(imgIn.getMimeType(),imgOutOptl.get().getMimeType());


    }

    @Test
    public void getImagesByIdJobTest(){

        List<ImageDto> dtos1 = new ArrayList<>();

        dtos1.add(new ImageDto(imgInfo1,imgType));
        dtos1.add(new ImageDto(imgInfo2,imgType));
        dtos1.add(new ImageDto(imgInfo2,imgType));

        Collection<Image> list = imageDao.createImages(dtos1);

        Map<String,Object> jobMap = new HashMap<>();
        jobMap.put("j_description",JOB_DESCRIPTION);
        jobMap.put("j_category",JOB_CATEGORY);
        jobMap.put("j_job_provided",JOB_PROVIDED);
        jobMap.put("j_provider_id",USER_ID);
        jobMap.put("j_price",JOB_PRICE);

        jobJdbcInsert.execute(jobMap);

        Map<String, Object> imageJobMap = new HashMap<>();
        Collection<Long> imagesId = new LinkedList<>();

        for (Image image : list) {
            imageJobMap.put("ji_image_id", image.getImageId());
            imageJobMap.put("ji_job_id", JOB_ID);
            imagesId.add(image.getImageId());
            jobImageJdbcInsert.execute(imageJobMap);
        }

        Collection<Image> imgs=imageDao.getImagesByJobId(1);

        assertNotNull(imgs);
        assertEquals(imgs.size(),list.size());

    }


}
