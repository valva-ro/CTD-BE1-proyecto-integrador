package com.valva.proyectointegrador.service;

import java.util.List;

public interface CRUDService<T> {
    T buscar(Integer id);
    T crear(T t);
    T actualizar(T t);
    void eliminar(Integer id);
    List<T> consultarTodos();
}
