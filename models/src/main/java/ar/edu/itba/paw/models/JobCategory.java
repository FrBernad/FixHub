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
    TECHISTA,
    LIMPIEZA,
    MANTENIMIENTO,
    ENTREGA,
    MUDANZA,
    NINERA,
    PASEADOR_DE_PERRO,
    CUIDADOR_DE_ANCIANO,
    CHEF,
    CATERING,
    VIDRIERO,
    FUMIGADOR,
    FOTOGRAFO;

   public static boolean contains(String value){
        return Arrays.stream(values()).map(Enum::name).anyMatch(code -> code.equals(value));
    }

}
