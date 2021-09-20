package com.valva.proyectointegrador.service;

import com.valva.proyectointegrador.dto.PacienteDto;

import java.util.List;

public interface IPacienteService extends CRUDService<PacienteDto> {
    PacienteDto buscar(Integer dni);
    List<PacienteDto> buscar(String nombre);
    List<PacienteDto> buscar(String nombre, String apellido);
}
