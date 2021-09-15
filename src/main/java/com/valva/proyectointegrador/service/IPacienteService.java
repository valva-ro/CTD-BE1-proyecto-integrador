package com.valva.proyectointegrador.service;

import com.valva.proyectointegrador.model.Paciente;

import java.util.List;

public interface IPacienteService extends CRUDService<Paciente> {
    Paciente buscar(Integer dni);
    List<Paciente> buscar(String nombre);
    List<Paciente> buscar(String nombre, String apellido);
}
