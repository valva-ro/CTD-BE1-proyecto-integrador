package com.valva.proyectointegrador.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class OdontologoDto {

    private Integer id;
    private String nombre;
    private String apellido;
    private Integer matricula;

}
