package ar.edu.itba.paw.webapp.dto.response;

import java.util.Collection;

public class SearchOptionDto {

    private String key;
    private Collection<String> values;

    public SearchOptionDto() {
    }

    public SearchOptionDto(String key, Collection<String> values) {
        this.key = key;
        this.values = values;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Collection<String> getValues() {
        return values;
    }

    public void setValues(Collection<String> values) {
        this.values = values;
    }
}
