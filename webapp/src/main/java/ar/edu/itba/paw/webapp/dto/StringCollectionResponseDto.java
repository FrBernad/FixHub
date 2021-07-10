package ar.edu.itba.paw.webapp.dto;

import java.util.Collection;

public class StringCollectionResponseDto {

    Collection<String> values;


    public StringCollectionResponseDto() {
    }

    public StringCollectionResponseDto(Collection<String> values) {
        this.values = values;
    }

    public Collection<String> getValues() {
        return values;
    }

    public void setValues(Collection<String> values) {
        this.values = values;
    }
}
