package com.valva.proyectointegrador.service;

import com.valva.proyectointegrador.exceptions.service.PacienteServiceException;
import com.valva.proyectointegrador.model.PacienteDto;

import java.util.List;

public interface IPacienteService extends CRUDService<PacienteDto> {
    PacienteDto buscar(Integer dni) throws PacienteServiceException;
    List<PacienteDto> buscar(String nombre);
    List<PacienteDto> buscar(String nombre, String apellido);
}
