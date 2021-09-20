package com.valva.proyectointegrador.controllers.impl;

import com.valva.proyectointegrador.controllers.CRUDController;
import com.valva.proyectointegrador.exceptions.service.PacienteServiceException;
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
    public ResponseEntity<?> registrar(@RequestBody PacienteDto paciente) {
        ResponseEntity<?> response;
        paciente.setFechaIngreso(LocalDate.now());
        try {
            PacienteDto pacienteInsertado = pacienteService.crear(paciente);
            response = ResponseEntity.ok(pacienteInsertado);
        } catch (Exception e) {
            response = ResponseEntity.badRequest().body(e.getMessage());
        }
        return response;
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id) {
        ResponseEntity<?> response;
        PacienteDto paciente = null;
        try {
            paciente = pacienteService.buscarPorId(id);
            if (paciente != null) {
                response = ResponseEntity.ok(paciente);
            } else {
                response = ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            response = ResponseEntity.badRequest().body(e.getMessage());
        }
        return response;
    }

    @GetMapping(params = "dni")
    public ResponseEntity<PacienteDto> buscar(@RequestParam Integer dni) {
        ResponseEntity<PacienteDto> response;
        try {
            PacienteDto paciente = pacienteService.buscar(dni);
            response = ResponseEntity.ok(paciente);
        } catch (PacienteServiceException e) {
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
        try {
            actualizado = pacienteService.actualizar(paciente);
            response = ResponseEntity.ok(actualizado);
        } catch (Exception e) {
            response = ResponseEntity.badRequest().body(e.getMessage());
        }
        return response;
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Integer id) {
        pacienteService.eliminar(id);
        return ResponseEntity.ok("Se elimino el paciente con id " + id);
    }

    @Override
    @GetMapping
    public ResponseEntity<List<PacienteDto>> buscarTodos() {
        return ResponseEntity.ok(pacienteService.consultarTodos());
    }
}
