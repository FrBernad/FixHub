package ar.edu.itba.paw.models;

import javax.persistence.*;

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
}
