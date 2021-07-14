package ar.edu.itba.paw.models.pagination;

import java.util.Arrays;

public enum StatusOrderOptions {
    NEWEST,
    OLDER;

    public static boolean contains(String value) {
        return Arrays.stream(values()).map(Enum::name).anyMatch(code -> code.equals(value));
    }
}
