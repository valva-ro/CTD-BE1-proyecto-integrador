package com.valva.proyectointegrador.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface CRUDController<T> {
    ResponseEntity<?> registrar(@RequestBody T t);
    ResponseEntity<?> buscarPorId(@PathVariable Integer id);
    ResponseEntity<?> actualizar(@RequestBody T t);
    ResponseEntity<String> eliminar(@PathVariable Integer id);
    ResponseEntity<?> buscarTodos();
}
