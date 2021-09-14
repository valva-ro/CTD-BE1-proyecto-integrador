package com.valva.proyectointegrador.controllers.impl;

import com.valva.proyectointegrador.controllers.CRUDController;
import com.valva.proyectointegrador.model.Domicilio;
import com.valva.proyectointegrador.service.CRUDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/domicilios")
public class DomicilioController implements CRUDController<Domicilio> {

    @Autowired
    @Qualifier("domicilioService")
    private CRUDService<Domicilio> domicilioService;

    @Override
    @PostMapping()
    public ResponseEntity<Domicilio> registrar(@RequestBody Domicilio domicilio) {
        ResponseEntity<Domicilio> response;
        Domicilio domicilioInsertado = domicilioService.crear(domicilio);
        if (domicilioInsertado != null) {
            response = ResponseEntity.ok(domicilioInsertado);
        } else {
            response = ResponseEntity.badRequest().body(domicilio);
        }
        return response;
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<Domicilio> buscar(@PathVariable Integer id) {
        ResponseEntity<Domicilio> response;
        Domicilio domicilio = domicilioService.buscar(id);
        if (domicilio != null) {
            response = ResponseEntity.ok(domicilio);
        } else {
            response = ResponseEntity.notFound().build();
        }
        return response;
    }

    @Override
    @PutMapping()
    public ResponseEntity<Domicilio> actualizar(@RequestBody Domicilio domicilio) {
        ResponseEntity<Domicilio> response;
        Domicilio actualizado;
        if (domicilio.getId() != null && domicilioService.buscar(domicilio.getId()) != null) {
            actualizado = domicilioService.actualizar(domicilio);
            if (actualizado != null) {
                response = ResponseEntity.ok(actualizado);
            } else {
                response = ResponseEntity.badRequest().body(domicilio);
            }
        } else {
            response = ResponseEntity.notFound().build();
        }
        return response;
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Integer id) {
        ResponseEntity<String> response;
        if (domicilioService.buscar(id) != null) {
            domicilioService.eliminar(id);
            response = ResponseEntity.status(HttpStatus.NO_CONTENT).body("Eliminado");
        } else {
            response = ResponseEntity.notFound().build();
        }
        return response;
    }

    @Override
    @GetMapping
    public ResponseEntity<List<Domicilio>> buscarTodos() {
        return ResponseEntity.ok(domicilioService.consultarTodos());
    }
}
