package com.valva.proyectointegrador.service;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CRUDService<T> {
    T buscarPorId(Integer id) throws Exception;
    T crear(T t) throws Exception;
    T actualizar(T t) throws Exception;
    void eliminar(Integer id);
    List<T> consultarTodos();
}
