package ar.edu.itba.paw.models;

import java.util.Arrays;

public enum JobCategory {
    CARPINTERO,
    CATERING,
    CHEF,
    CUIDADOR_DE_ANCIANO,
    ELECTRICISTA,
    ENTREGA,
    FOTOGRAFO,
    FUMIGADOR,
    GASISTA,
    HERRERO,
    JARDINERO,
    LIMPIEZA,
    MANTENIMIENTO,
    MECANICO,
    MUDANZA,
    NINERA,
    PASEADOR_DE_PERRO,
    PLOMERO,
    PINTOR,
    TECHISTA,
    VIDRIERO;

   public static boolean contains(String value){
        return Arrays.stream(values()).map(Enum::name).anyMatch(code -> code.equals(value));
    }

}
