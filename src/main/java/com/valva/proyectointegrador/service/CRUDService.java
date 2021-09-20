package com.valva.proyectointegrador.service;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CRUDService<T> {
    T buscarPorId(Integer id);
    T crear(T t);
    T actualizar(T t);
    void eliminar(Integer id);
    List<T> consultarTodos();
}
