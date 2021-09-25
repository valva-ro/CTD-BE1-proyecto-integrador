package com.valva.proyectointegrador.model;

import lombok.*;

import java.time.LocalDateTime;

@Data
public class TurnoDto {

    private Integer id;
    private LocalDateTime fecha;
    private PacienteDto paciente;
    private OdontologoDto odontologo;

}
