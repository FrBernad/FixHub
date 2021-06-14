package ar.edu.itba.paw.models.image;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "images")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "images_i_id_seq")
    @SequenceGenerator(sequenceName = "images_i_id_seq", name = "images_i_id_seq", allocationSize = 1)
    @Column(name = "i_id", nullable = false)
    private Long id;

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

    public Image(Long id,byte[] data, String mimeType) {
        this.id = id;
        this.data = data;
        this.mimeType = mimeType;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        return Objects.equals(id, image.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


}


