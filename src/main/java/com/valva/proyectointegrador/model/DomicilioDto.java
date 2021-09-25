package com.valva.proyectointegrador.model;

import lombok.*;

@Data
public class DomicilioDto {

    private Integer id;
    private String calle;
    private Integer numero;
    private String localidad;
    private String provincia;

}
