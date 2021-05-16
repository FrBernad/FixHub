package ar.edu.itba.paw.models;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Objects;

@Entity
@Table(name = "images")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "images_i_id_seq")
    @SequenceGenerator(sequenceName = "images_i_id_seq", name = "images_i_id_seq", allocationSize = 1)
    @Column(name = "i_id", nullable = false)
    private Long imageId;

    @Column(name = "i_data", nullable = false, length = 30000000)
    @Basic(fetch = FetchType.LAZY, optional = false)
    private byte[] data;

    @Column(name = "i_mime_type", nullable = false)
    private String mimeType;

    /* default */
    protected Image() {
        // Just for Hibernate
    }

    public Image(byte[] data, String mimeType) {
        this.data = data;
        this.mimeType = mimeType;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public Long getImageId() {
        return imageId;
    }

    public void setImageId(Long imageId) {
        this.imageId = imageId;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Image)) return false;
        Image image = (Image) o;
        return Objects.equals(imageId, image.imageId) && Arrays.equals(data, image.data) && Objects.equals(mimeType, image.mimeType);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(imageId, mimeType);
        result = 31 * result + Arrays.hashCode(data);
        return result;
    }

    @Override
    public String toString() {
        return "Image{" +
            "imageId=" + imageId +
            ", data=" + Arrays.toString(data) +
            ", mimeType='" + mimeType + '\'' +
            '}';
    }
}


