package ar.edu.itba.paw.models;

import java.util.Arrays;

public enum OrderOptions {
    MOST_POPULAR,
    LESS_POPULAR,
    HIGHER_PRICE,
    LOWER_PICE;

    public static boolean contains(String value) {
        return Arrays.stream(values()).map(Enum::name).anyMatch(code -> code.equals(value));
    }
}
