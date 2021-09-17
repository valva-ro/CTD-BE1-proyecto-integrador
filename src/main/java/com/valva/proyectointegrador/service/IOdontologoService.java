package com.valva.proyectointegrador.service;

import com.valva.proyectointegrador.persistence.model.Odontologo;

import java.util.List;

public interface IOdontologoService extends CRUDService<Odontologo> {
    Odontologo buscar(Integer matricula);
    List<Odontologo> buscar(String nombre);
    List<Odontologo> buscar(String nombre, String apellido);
}
