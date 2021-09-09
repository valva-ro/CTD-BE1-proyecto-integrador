package com.valva.proyectointegrador.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

public class Util {
    public static java.sql.Date convertLocalDateToSqlDate(LocalDate fechaAConvertir) {
        return java.sql.Date.valueOf(fechaAConvertir);
    }

    public static LocalDate convertirSqlDateALocalDate(java.sql.Date fecha) {
        return Instant.ofEpochMilli(fecha.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }
}
