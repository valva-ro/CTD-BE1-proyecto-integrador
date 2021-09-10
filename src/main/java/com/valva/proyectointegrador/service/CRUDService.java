package com.valva.proyectointegrador.service;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface CRUDService<T> {
    T buscar(Integer id);
    T crear(T t);
    T actualizar(T t);
    void eliminar(Integer id);
    List<T> consultarTodos();
}
