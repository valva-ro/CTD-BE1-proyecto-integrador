package com.valva.proyectointegrador.controllers.impl;

import com.valva.proyectointegrador.controllers.CRUDController;
import com.valva.proyectointegrador.model.PacienteDto;
import com.valva.proyectointegrador.service.IPacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/pacientes")
public class PacienteController implements CRUDController<PacienteDto> {

    @Autowired
    @Qualifier("pacienteService")
    private IPacienteService pacienteService;

    @Override
    @PostMapping()
    public ResponseEntity<PacienteDto> registrar(@RequestBody PacienteDto paciente) {
        ResponseEntity<PacienteDto> response;
        paciente.setFechaIngreso(LocalDate.now());
        PacienteDto pacienteInsertado = pacienteService.crear(paciente);
        if (pacienteInsertado != null) {
            response = ResponseEntity.ok(pacienteInsertado);
        } else {
            response = ResponseEntity.badRequest().body(paciente);
        }
        return response;
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<PacienteDto> buscarPorId(@PathVariable Integer id) {
        ResponseEntity<PacienteDto> response;
        PacienteDto paciente = pacienteService.buscarPorId(id);
        if (paciente != null) {
            response = ResponseEntity.ok(paciente);
        } else {
            response = ResponseEntity.notFound().build();
        }
        return response;
    }

    @GetMapping(params = "dni")
    public ResponseEntity<PacienteDto> buscar(@RequestParam Integer dni) {
        ResponseEntity<PacienteDto> response;
        PacienteDto paciente = pacienteService.buscar(dni);
        if (paciente != null) {
            response = ResponseEntity.ok(paciente);
        } else {
            response = ResponseEntity.notFound().build();
        }
        return response;
    }

    @GetMapping(params = "nombre")
    public ResponseEntity<List<PacienteDto>> buscar(@RequestParam String nombre) {
        List<PacienteDto> pacientes = pacienteService.buscar(nombre);
        return ResponseEntity.ok(pacientes);
    }

    @GetMapping(params = {"nombre", "apellido"})
    public ResponseEntity<List<PacienteDto>> buscar(@RequestParam String nombre, @RequestParam String apellido) {
        List<PacienteDto> pacientes = pacienteService.buscar(nombre, apellido);
        return ResponseEntity.ok(pacientes);
    }

    @Override
    @PutMapping()
    public ResponseEntity<?> actualizar(@RequestBody PacienteDto paciente) {
        ResponseEntity<?> response;
        PacienteDto actualizado;
        if (paciente.getId() != null && pacienteService.buscarPorId(paciente.getId()) != null) {
            actualizado = pacienteService.actualizar(paciente);
            if (actualizado != null) {
                response = ResponseEntity.ok(actualizado);
            } else {
                response = ResponseEntity.badRequest().body("No se pudo actualizar el paciente");
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
        if (pacienteService.buscarPorId(id) != null) {
            pacienteService.eliminar(id);
            response = ResponseEntity.ok("Se elimino el paciente con id " + id);
        } else {
            response = ResponseEntity.notFound().build();
        }
        return response;
    }

    @Override
    @GetMapping
    public ResponseEntity<List<PacienteDto>> buscarTodos() {
        return ResponseEntity.ok(pacienteService.consultarTodos());
    }
}
