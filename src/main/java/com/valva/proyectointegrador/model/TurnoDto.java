package com.valva.proyectointegrador.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class TurnoDto {

    private Integer id;
    private LocalDate fecha;
    private PacienteDto paciente;
    private OdontologoDto odontologo;

}
