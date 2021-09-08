package com.valva.proyectointegrador.repository;

import java.sql.SQLException;
import java.util.List;

public interface IRepository<T> {
    T insertarNuevo(T t) throws Exception;
    List<T> consultarTodos() throws SQLException;
    T consultarPorId(Integer id) throws Exception;
    T actualizar(T t) throws Exception;
    void eliminar(Integer id) throws SQLException;
}
