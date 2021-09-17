package com.valva.proyectointegrador.service;

import com.valva.proyectointegrador.persistence.model.Domicilio;

import java.util.List;

public interface IDomicilioService extends CRUDService<Domicilio> {
    List<Domicilio> buscar(String calle);
    List<Domicilio> buscar(String calle, Integer numero);
    Domicilio buscar(String calle, Integer numero, String localidad, String provincia);
}
