package ar.edu.itba.paw.models;

import java.util.Arrays;

public enum JobCategory {
    MECANICO,
    ELECTRICISTA,
    PLOMERO,
    JARDINERO,
    GASISTA,
    CARPINTERO,
    PINTOR,
    HERRERO,
    TECHISTA;

   public static boolean contains(String value){
        return Arrays.stream(values()).map(Enum::name).anyMatch(code -> code.equals(value));
    }

}
