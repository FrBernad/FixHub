package ar.edu.itba.paw.models;


public class Image {

    private long imageId;
    private byte[] data;
    private String mimeType;

    public Image(long imageId, byte[] data, String mimeType) {
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

    public long getImageId() {
        return imageId;
    }

    public void setImageId(long imageId) {
        this.imageId = imageId;
    }

    public byte[] getData() {
        return data;
    }
    public void setData(byte[] data) {
        this.data = data;
    }
}
