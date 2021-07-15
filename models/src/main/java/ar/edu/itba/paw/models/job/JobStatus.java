package ar.edu.itba.paw.models.job;

import java.util.Arrays;

public enum JobStatus {
    PENDING,
    IN_PROGRESS,
    FINISHED,
    REJECTED,
    CANCELED;

    public static boolean contains(String value) {
        return Arrays.stream(values()).map(Enum::name).anyMatch(code -> code.equals(value));
    }
}