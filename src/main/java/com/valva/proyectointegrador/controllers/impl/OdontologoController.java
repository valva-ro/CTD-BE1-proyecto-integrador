package com.valva.proyectointegrador.controllers.impl;

import com.valva.proyectointegrador.controllers.CRUDController;
import com.valva.proyectointegrador.model.Odontologo;
import com.valva.proyectointegrador.service.CRUDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/odontologos")
public class OdontologoController implements CRUDController<Odontologo> {

    @Autowired
    @Qualifier("odontologoService")
    private CRUDService<Odontologo> odontologoService;

    @Override
    @PostMapping()
    public ResponseEntity<Odontologo> registrar(@RequestBody Odontologo odontologo) {
        ResponseEntity<Odontologo> response;
        Odontologo odontologoInsertado = odontologoService.crear(odontologo);
        if (odontologoInsertado != null) {
            response = ResponseEntity.ok(odontologoInsertado);
        } else {
            response = ResponseEntity.badRequest().body(odontologo);
        }
        return response;
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<Odontologo> buscar(@PathVariable Integer id) {
        ResponseEntity<Odontologo> response;
        Odontologo odontologo = odontologoService.buscar(id);
        if (odontologo != null) {
            response = ResponseEntity.ok(odontologo);
        } else {
            response = ResponseEntity.notFound().build();
        }
        return response;
    }

    @Override
    @PutMapping()
    public ResponseEntity<Odontologo> actualizar(@RequestBody Odontologo odontologo) {
        ResponseEntity<Odontologo> response;
        Odontologo actualizado;
        if (odontologo.getId() != null && odontologoService.buscar(odontologo.getId()) != null) {
            actualizado = odontologoService.actualizar(odontologo);
            if (actualizado != null) {
                response = ResponseEntity.ok(actualizado);
            } else {
                response = ResponseEntity.badRequest().body(odontologo);
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
        if (odontologoService.buscar(id) != null) {
            odontologoService.eliminar(id);
            response = ResponseEntity.status(HttpStatus.NO_CONTENT).body("Eliminado");
        } else {
            response = ResponseEntity.notFound().build();
        }
        return response;
    }

    @Override
    @GetMapping
    public ResponseEntity<List<Odontologo>> buscarTodos(){
        return ResponseEntity.ok(odontologoService.consultarTodos());
    }
}
