package com.valva.proyectointegrador.service;

import com.valva.proyectointegrador.exceptions.service.OdontologoServiceException;
import com.valva.proyectointegrador.model.OdontologoDto;

import java.util.List;

public interface IOdontologoService extends CRUDService<OdontologoDto> {
    OdontologoDto buscar(Integer matricula) throws OdontologoServiceException;
    List<OdontologoDto> buscar(String nombre);
    List<OdontologoDto> buscar(String nombre, String apellido);
}
