package com.valva.proyectointegrador.service;

import com.valva.proyectointegrador.model.DomicilioDto;

import java.util.List;

public interface IDomicilioService extends CRUDService<DomicilioDto> {
    List<DomicilioDto> buscar(String calle);
    List<DomicilioDto> buscar(String calle, Integer numero);
    DomicilioDto buscar(String calle, Integer numero, String localidad, String provincia) throws Exception;
}
