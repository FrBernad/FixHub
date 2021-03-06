package ar.edu.itba.paw.models.image;

public class NewImageDto {
    private byte[] data;
    private String mimeType;

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public NewImageDto(byte[] data, String mimeType) {
        this.data = data;
        this.mimeType = mimeType;
    }
}