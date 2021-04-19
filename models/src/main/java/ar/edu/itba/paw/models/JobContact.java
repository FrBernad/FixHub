package ar.edu.itba.paw.models;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class JobContact {
    private ContactInfo contactInfo;
    private ContactUser client;
    private String message;
    private LocalDateTime date;

    public JobContact(ContactInfo contactInfo, ContactUser client, String message, LocalDateTime date) {
        this.contactInfo = contactInfo;
        this.client = client;
        this.message = message;
        this.date = date;
    }

    public ContactInfo getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(ContactInfo contactInfo) {
        this.contactInfo = contactInfo;
    }

    public ContactUser getClient() {
        return client;
    }

    public void setClient(ContactUser client) {
        this.client = client;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }


}
