package ar.edu.itba.paw.models;


public class Image {

    private Number imageId;
    private byte[] data;

    public Image(Number imageId, byte[] data) {
        this.imageId = imageId;
        this.data = data;
    }

    public Number getImageId() {
        return imageId;
    }

    public void setImageId(Number imageId) {
        this.imageId = imageId;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
