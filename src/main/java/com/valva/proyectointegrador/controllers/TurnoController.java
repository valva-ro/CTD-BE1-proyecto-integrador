package com.valva.proyectointegrador.controllers;

import com.valva.proyectointegrador.model.Turno;
import com.valva.proyectointegrador.service.TurnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/turnos")
public class TurnoController {

    @Autowired
    public TurnoService turnoService;

    @PostMapping()
    public ResponseEntity<Turno> registrar(@RequestBody Turno turno) {
        ResponseEntity<Turno> response;
        Turno turnoInsertado = turnoService.crear(turno);
        if (turnoInsertado != null) {
            response = ResponseEntity.ok(turnoInsertado);
        } else {
            response = ResponseEntity.badRequest().build();
        }
        return response;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Turno> buscar(@PathVariable Integer id) {
        ResponseEntity<Turno> response;
        Turno turno = turnoService.buscar(id);
        if (turno != null) {
            response = ResponseEntity.ok(turno);
        } else {
            response = ResponseEntity.notFound().build();
        }
        return response;
    }

    @PutMapping()
    public ResponseEntity<Turno> actualizar(@RequestBody Turno turno) {
        ResponseEntity<Turno> response;
        if (turno.getId() != null && turnoService.buscar(turno.getId()) != null) {
            response = ResponseEntity.ok(turnoService.actualizar(turno));
        } else {
            response = ResponseEntity.notFound().build();
        }
        return response;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Integer id) {
        ResponseEntity<String> response;
        if (turnoService.buscar(id) != null) {
            turnoService.eliminar(id);
            response = ResponseEntity.status(HttpStatus.NO_CONTENT).body("Eliminado");
        } else {
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return response;
    }

    @GetMapping
    public ResponseEntity<List<Turno>> buscarTodos() {
        return ResponseEntity.ok(turnoService.consultarTodos());
    }
}
