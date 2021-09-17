package com.valva.proyectointegrador.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class DomicilioDto {

    private Integer id;
    private String calle;
    private Integer numero;
    private String localidad;
    private String provincia;

}
