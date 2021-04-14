package ar.edu.itba.paw.models;


public class Image {

    private Number imageId;
    private byte[] data;
    private String mimeType;

    public Image(Number imageId, byte[] data, String mimeType) {
        this.imageId = imageId;
        this.data = data;
        this.mimeType = mimeType;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
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
