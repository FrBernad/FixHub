package ar.edu.itba.paw.interfaces.persistance;


public interface ImageDao {


    Image createImage(MultipartFile file, User provider);
}
