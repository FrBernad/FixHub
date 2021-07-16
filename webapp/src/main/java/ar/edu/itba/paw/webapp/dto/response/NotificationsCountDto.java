package ar.edu.itba.paw.webapp.dto.response;

public class NotificationsCountDto {
    private int count;

    public NotificationsCountDto() {
    }

    public NotificationsCountDto(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
