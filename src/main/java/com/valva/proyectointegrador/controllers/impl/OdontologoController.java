package com.valva.proyectointegrador.controllers.impl;

import com.valva.proyectointegrador.controllers.CRUDController;
import com.valva.proyectointegrador.persistence.model.Odontologo;
import com.valva.proyectointegrador.service.IOdontologoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/odontologos")
public class OdontologoController implements CRUDController<Odontologo> {

    @Autowired
    @Qualifier("odontologoService")
    private IOdontologoService odontologoService;

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
    public ResponseEntity<Odontologo> buscarPorId(@PathVariable Integer id) {
        ResponseEntity<Odontologo> response;
        Odontologo odontologo = odontologoService.buscarPorId(id);
        if (odontologo != null) {
            response = ResponseEntity.ok(odontologo);
        } else {
            response = ResponseEntity.notFound().build();
        }
        return response;
    }

    @GetMapping(params = "matricula")
    public ResponseEntity<Odontologo> buscar(@RequestParam Integer matricula) {
        ResponseEntity<Odontologo> response;
        Odontologo odontologo = odontologoService.buscar(matricula);
        if (odontologo != null) {
            response = ResponseEntity.ok(odontologo);
        } else {
            response = ResponseEntity.notFound().build();
        }
        return response;
    }

    @GetMapping(params = "nombre")
    public ResponseEntity<List<Odontologo>> buscar(@RequestParam String nombre) {
        List<Odontologo> odontologos = odontologoService.buscar(nombre);
        return ResponseEntity.ok(odontologos);
    }

    @GetMapping(params = {"nombre", "apellido"})
    public ResponseEntity<List<Odontologo>> buscar(@RequestParam String nombre, @RequestParam String apellido) {
        List<Odontologo> odontologos = odontologoService.buscar(nombre, apellido);
        return ResponseEntity.ok(odontologos);
    }

    @Override
    @PutMapping()
    public ResponseEntity<Odontologo> actualizar(@RequestBody Odontologo odontologo) {
        ResponseEntity<Odontologo> response;
        Odontologo actualizado;
        if (odontologo.getId() != null && odontologoService.buscarPorId(odontologo.getId()) != null) {
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
        if (odontologoService.buscarPorId(id) != null) {
            odontologoService.eliminar(id);
            response = ResponseEntity.ok("Se elimino el odontologo con id " + id);
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
