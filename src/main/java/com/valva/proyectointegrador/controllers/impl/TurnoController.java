package com.valva.proyectointegrador.controllers.impl;

import com.valva.proyectointegrador.controllers.CRUDController;
import com.valva.proyectointegrador.model.Turno;
import com.valva.proyectointegrador.service.CRUDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/turnos")
public class TurnoController implements CRUDController<Turno> {

    @Autowired
    @Qualifier("turnoService")
    private CRUDService<Turno> turnoService;

    @Override
    @PostMapping()
    public ResponseEntity<Turno> registrar(@RequestBody Turno turno) {
        ResponseEntity<Turno> response;
        Turno turnoInsertado = turnoService.crear(turno);
        if (turnoInsertado != null) {
            response = ResponseEntity.ok(turnoInsertado);
        } else {
            response = ResponseEntity.badRequest().body(turno);
        }
        return response;
    }

    @Override
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

    @Override
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

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Integer id) {
        ResponseEntity<String> response;
        if (turnoService.buscar(id) != null) {
            turnoService.eliminar(id);
            response = ResponseEntity.status(HttpStatus.NO_CONTENT).body("Eliminado");
        } else {
            response = ResponseEntity.notFound().build();
        }
        return response;
    }

    @Override
    @GetMapping
    public ResponseEntity<List<Turno>> buscarTodos() {
        return ResponseEntity.ok(turnoService.consultarTodos());
    }
}
