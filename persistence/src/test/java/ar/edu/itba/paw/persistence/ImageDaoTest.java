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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.*;

import static org.junit.Assert.*;

@Transactional
@Sql(scripts = "classpath:image-dao-test.sql")
@ContextConfiguration(classes = TestConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class ImageDaoTest {

    @Autowired
    private DataSource ds;

    @Autowired
    private ImageDao imageDao;

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert imageSimpleJdbcInsert;
    private SimpleJdbcInsert jobImageSimpleJdbcInsert;



    //User data
    private static final Integer USER_ID=1;

    //Job data
    private static final String JOB_PROVIDED = "Arreglo de mesas y sillas";
    private static final JobCategory JOB_CATEGORY = JobCategory.CARPINTERO;
    private static final String JOB_DESCRIPTION = "Trabajo en muebles de roble y pino";
    private static final BigDecimal JOB_PRICE = BigDecimal.valueOf(1500);
    private static final Integer JOB_ID=1;
    private static final boolean JOB_PAUSE=false;


    private static final byte[] imgInfo1={0,1,2,3,4,5,6,7,8,9};
    private static final byte[] imgInfo2={0,9,8,7,6,5,4,3,2,1};
    private static final byte[] imgInfo3={2,2,1,3,0,4,9,4,9,9};

    private static final String imgType ="image/jpeg";

    private static final ImageDto IMAGEDTO1 = new ImageDto(imgInfo1,imgType);
    private static final ImageDto IMAGEDTO2 = new ImageDto(imgInfo2,imgType);
    private static final ImageDto IMAGEDTO3 = new ImageDto(imgInfo3,imgType);


    @Before
    public void setUp(){
        jdbcTemplate=new JdbcTemplate(ds);
        imageSimpleJdbcInsert = new SimpleJdbcInsert(ds).withTableName("IMAGES").usingGeneratedKeyColumns("i_id");
        jobImageSimpleJdbcInsert = new SimpleJdbcInsert(ds).withTableName("JOB_IMAGE");

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

        dtos1.add(IMAGEDTO1);
        dtos1.add(IMAGEDTO2);
        dtos1.add(IMAGEDTO3);


        List<Image> dtos2 = imageDao.createImages(dtos1);

        assertNotNull(dtos2);

        assertEquals(dtos1.size(), JdbcTestUtils.countRowsInTable(jdbcTemplate,"images"));

        for (int i = 0; i < dtos1.size() ; i++) {
            assertArrayEquals(dtos1.get(i).getData(),dtos2.get(i).getData());
            assertEquals(dtos1.get(i).getMimeType(),dtos2.get(i).getMimeType());
        }

    }


    @Test

    public void getImageByIdTest(){
        final Map<String, Object> imageInfo = new HashMap<>();
        imageInfo.put("i_data", IMAGEDTO1.getData());
        imageInfo.put("i_mime_type", IMAGEDTO1.getMimeType());
        final long id = imageSimpleJdbcInsert.executeAndReturnKey(imageInfo).longValue();
        Image image = new Image(id,IMAGEDTO1.getData(),IMAGEDTO1.getMimeType());

        Optional<Image> maybeImg = imageDao.getImageById(image.getImageId());

        assertTrue(maybeImg.isPresent());
        assertEquals(image.getImageId(),maybeImg.get().getImageId());
        assertArrayEquals(image.getData(), maybeImg.get().getData());
        assertEquals(image.getMimeType(),maybeImg.get().getMimeType());


    }

    @Test
    public void deleteImagesByJobId(){
        List<ImageDto> imageDtos = new LinkedList<>();
        imageDtos.add(IMAGEDTO1);
        imageDtos.add(IMAGEDTO2);
        imageDtos.add(IMAGEDTO3);

        final Map<String, Object> imageInfo = new HashMap<>();
        long id;

        List<Image> images = new LinkedList<>();

        for(ImageDto imageDto : imageDtos){
            imageInfo.put("i_data", imageDto.getData());
            imageInfo.put("i_mime_type", imageDto.getMimeType());
            id = imageSimpleJdbcInsert.executeAndReturnKey(imageInfo).longValue();
            images.add(new Image(id,imageDto.getData(),imageDto.getMimeType()));
        }

        assertEquals(imageDtos.size(),JdbcTestUtils.countRowsInTable(jdbcTemplate,"images"));

        List<Long> imagesId = new LinkedList<>();
        for(Image aux : images){
            imagesId.add(aux.getImageId());
        }

        imageDao.deleteImagesById(imagesId);


        assertEquals(0,JdbcTestUtils.countRowsInTable(jdbcTemplate,"images"));


    }

    @Test
    public void getImagesByIdJobTest(){

        List<ImageDto> imageDtos = new LinkedList<>();
        imageDtos.add(IMAGEDTO1);
        imageDtos.add(IMAGEDTO2);
        imageDtos.add(IMAGEDTO3);

        final Map<String, Object> imageInfo = new HashMap<>();
        long id;

        List<Image> images = new LinkedList<>();

        for(ImageDto imageDto : imageDtos){
            imageInfo.put("i_data", imageDto.getData());
            imageInfo.put("i_mime_type", imageDto.getMimeType());
            id = imageSimpleJdbcInsert.executeAndReturnKey(imageInfo).longValue();
            images.add(new Image(id,imageDto.getData(),imageDto.getMimeType()));
        }

        Map<String, Object> imageJobMap = new HashMap<>();

        for (Image image : images) {
            imageJobMap.put("ji_image_id", image.getImageId());
            imageJobMap.put("ji_job_id", JOB_ID);
            jobImageSimpleJdbcInsert.execute(imageJobMap);
        }

        Collection<Image> jobImages = imageDao.getImagesByJobId(JOB_ID);

        assertNotNull(jobImages);
        assertEquals(jobImages.size(),images.size());

    }


}
