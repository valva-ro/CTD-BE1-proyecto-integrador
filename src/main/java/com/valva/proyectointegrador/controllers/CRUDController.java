package com.valva.proyectointegrador.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface CRUDController<T> {
    ResponseEntity<T> registrar(@RequestBody T t);
    ResponseEntity<T> buscar(@PathVariable Integer id);
    ResponseEntity<T> actualizar(@RequestBody T t);
    ResponseEntity<String> eliminar(@PathVariable Integer id);
    ResponseEntity<List<T>> buscarTodos();
}
