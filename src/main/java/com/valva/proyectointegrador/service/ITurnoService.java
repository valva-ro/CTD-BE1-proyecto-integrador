package com.valva.proyectointegrador.service;

import com.valva.proyectointegrador.model.Turno;

import java.util.List;

public interface ITurnoService extends CRUDService<Turno> {
    List<Turno> buscar(String nombrePaciente, String apellidoPaciente, String nombreOdontologo, String apellidoOdontologo);
    List<Turno> buscar(String nombreOdontologo, String apellidoOdontologo);
    List<Turno> buscar(Integer matricula, Integer dni);
}
