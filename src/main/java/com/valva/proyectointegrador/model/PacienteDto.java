package com.valva.proyectointegrador.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PacienteDto {

    private Integer id;
    private String nombre;
    private String apellido;
    private Integer dni;
    private LocalDate fechaIngreso;
    private DomicilioDto domicilio;

}
