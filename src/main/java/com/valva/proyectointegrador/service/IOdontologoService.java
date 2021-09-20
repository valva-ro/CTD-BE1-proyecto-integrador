package com.valva.proyectointegrador.service;

import com.valva.proyectointegrador.dto.OdontologoDto;

import java.util.List;

public interface IOdontologoService extends CRUDService<OdontologoDto> {
    OdontologoDto buscar(Integer matricula);
    List<OdontologoDto> buscar(String nombre);
    List<OdontologoDto> buscar(String nombre, String apellido);
}
