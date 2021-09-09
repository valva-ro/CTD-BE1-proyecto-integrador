package com.valva.proyectointegrador.controllers;

import com.valva.proyectointegrador.model.Paciente;
import com.valva.proyectointegrador.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/pacientes")
public class PacienteController implements CRUDController<Paciente> {

    @Autowired
    public PacienteService pacienteService;

    @Override
    @PostMapping()
    public ResponseEntity<Paciente> registrar(@RequestBody Paciente paciente) {
        ResponseEntity<Paciente> response;
        paciente.setFechaIngreso(LocalDate.now());
        Paciente pacienteInsertado = pacienteService.crear(paciente);
        if (pacienteInsertado != null) {
            response = ResponseEntity.ok(pacienteInsertado);
        } else {
            response = ResponseEntity.badRequest().body(paciente);
        }
        return response;
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<Paciente> buscar(@PathVariable Integer id) {
        ResponseEntity<Paciente> response;
        Paciente paciente = pacienteService.buscar(id);
        if (paciente != null) {
            response = ResponseEntity.ok(paciente);
        } else {
            response = ResponseEntity.notFound().build();
        }
        return response;
    }

    @Override
    @PutMapping()
    public ResponseEntity<Paciente> actualizar(@RequestBody Paciente paciente) {
        ResponseEntity<Paciente> response;
        Paciente actualizado;
        if (paciente.getId() != null && pacienteService.buscar(paciente.getId()) != null) {
            actualizado = pacienteService.actualizar(paciente);
            if (actualizado != null) {
                response = ResponseEntity.ok(actualizado);
            } else {
                response = ResponseEntity.badRequest().body(paciente);
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
        if (pacienteService.buscar(id) != null) {
            pacienteService.eliminar(id);
            response = ResponseEntity.status(HttpStatus.NO_CONTENT).body("Eliminado");
        } else {
            response = ResponseEntity.notFound().build();
        }
        return response;
    }

    @Override
    @GetMapping
    public ResponseEntity<List<Paciente>> buscarTodos() {
        return ResponseEntity.ok(pacienteService.consultarTodos());
    }
}
