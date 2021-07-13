package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistance.ImageDao;
import ar.edu.itba.paw.models.image.Image;
import ar.edu.itba.paw.models.image.NewImageDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import java.util.*;

import static org.junit.Assert.*;

@Rollback
@Transactional
@ContextConfiguration(classes = TestConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class ImageDaoTest {

    @Autowired
    private DataSource ds;

    @Autowired
    private ImageDao imageDao;

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert imageSimpleJdbcInsert;

    @PersistenceContext
    private EntityManager em;

    private static final byte[] imgInfo1={0,1,2,3,4,5,6,7,8,9};
    private static final byte[] imgInfo2={0,9,8,7,6,5,4,3,2,1};
    private static final byte[] imgInfo3={2,2,1,3,0,4,9,4,9,9};

    private static final String imgType ="image/jpeg";

    private static final Long IMAGEID = 1L;
    private static final NewImageDto IMAGEDTO_1 = new NewImageDto(imgInfo1,imgType);
    private static final NewImageDto IMAGEDTO_2 = new NewImageDto(imgInfo2,imgType);
    private static final NewImageDto IMAGEDTO_3 = new NewImageDto(imgInfo3,imgType);


    @Before
    public void setUp(){
        jdbcTemplate=new JdbcTemplate(ds);
        imageSimpleJdbcInsert = new SimpleJdbcInsert(ds).withTableName("IMAGES");
    }

    @Test
    public void testCreateImage() {

        final NewImageDto imgDto = new NewImageDto(imgInfo1, imgType);
        final Image img = imageDao.createImage(imgDto);

        assertNotNull(img);
        assertEquals(imgInfo1,img.getData());
        assertEquals(imgType,img.getMimeType());

    }

//    @Test
//    public void testCreateImages(){
//
//        List<NewImageDto> dtos1 = new ArrayList<>();
//
//        dtos1.add(IMAGEDTO_1);
//        dtos1.add(IMAGEDTO_2);
//        dtos1.add(IMAGEDTO_3);
//
//        Set<Image> dtos2 = imageDao.createImages(dtos1);
//
//        assertNotNull(dtos2);
//
//        Image[] dtos2Vec= dtos2.toArray(new Image[0]);
//
//        for (int i = 0; i < dtos1.size() ; i++) {
//            assertArrayEquals(dtos1.get(i).getData(),dtos2Vec[i].getData());
//            assertEquals(dtos1.get(i).getMimeType(),dtos2Vec[i].getMimeType());
//        }
//
//    }


    @Test
    public void testGetImageById(){

        JdbcTestUtils.deleteFromTables(jdbcTemplate,"images");

        final Map<String, Object> imageInfo = new HashMap<>();
        imageInfo.put("i_id",IMAGEID);
        imageInfo.put("i_data", IMAGEDTO_1.getData());
        imageInfo.put("i_mime_type", IMAGEDTO_1.getMimeType());

        imageSimpleJdbcInsert.execute(imageInfo);
        Optional<Image> maybeImg = imageDao.getImageById(IMAGEID);

        assertTrue(maybeImg.isPresent());
        assertEquals(IMAGEID,maybeImg.get().getId());
        assertArrayEquals(IMAGEDTO_1.getData(), maybeImg.get().getData());
        assertEquals(IMAGEDTO_1.getMimeType(),maybeImg.get().getMimeType());


    }

    @Test
    public void testDeleteImagesById(){

        List<NewImageDto> newImageDtos = new LinkedList<>();
        newImageDtos.add(IMAGEDTO_1);
        newImageDtos.add(IMAGEDTO_2);
        newImageDtos.add(IMAGEDTO_3);

        final Map<String, Object> imageInfo = new HashMap<>();

        List<Image> images = new LinkedList<>();

        long i=0;
        for(NewImageDto newImageDto : newImageDtos){
            imageInfo.put("i_id",i);
            imageInfo.put("i_data", newImageDto.getData());
            imageInfo.put("i_mime_type", newImageDto.getMimeType());
            imageSimpleJdbcInsert.execute(imageInfo);
            images.add(new Image(i, newImageDto.getData(), newImageDto.getMimeType()));
            i++;
        }

        assertEquals(newImageDtos.size(),JdbcTestUtils.countRowsInTable(jdbcTemplate,"images"));

        List<Long> imagesId = new LinkedList<>();
        for(Image aux : images){
            imagesId.add(aux.getId());
        }
        em.joinTransaction();
        imageDao.deleteImagesById(imagesId);

        assertEquals(0,JdbcTestUtils.countRowsInTable(jdbcTemplate,"images"));

    }

    @Test
    public void testGetImagesById(){

        List<NewImageDto> newImageDtos = new LinkedList<>();
        newImageDtos.add(IMAGEDTO_1);
        newImageDtos.add(IMAGEDTO_2);
        newImageDtos.add(IMAGEDTO_3);

        final Map<String, Object> imageInfo = new HashMap<>();

        List<Image> images = new LinkedList<>();
        Set<Long> ids = new HashSet<>();

        long i=0;

        for(NewImageDto newImageDto : newImageDtos){
            imageInfo.put("i_id",i);
            imageInfo.put("i_data", newImageDto.getData());
            imageInfo.put("i_mime_type", newImageDto.getMimeType());
            imageSimpleJdbcInsert.execute(imageInfo);
            images.add(new Image(i, newImageDto.getData(), newImageDto.getMimeType()));
            ids.add(i);
            i++;
        }

        em.joinTransaction();
        Collection<Image> jobImages = imageDao.getImagesById(ids);

        assertNotNull(jobImages);
        assertEquals(jobImages.size(),images.size());

    }


}
