package com.valva.proyectointegrador.controllers.impl;

import com.valva.proyectointegrador.controllers.CRUDController;
import com.valva.proyectointegrador.exceptions.BadRequestException;
import com.valva.proyectointegrador.exceptions.ResourceNotFoundException;
import com.valva.proyectointegrador.model.PacienteDto;
import com.valva.proyectointegrador.service.IPacienteService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/pacientes")
public class PacienteController implements CRUDController<PacienteDto> {

    @Qualifier("pacienteService")
    private final IPacienteService pacienteService;

    public PacienteController(IPacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

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
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id) throws BadRequestException, ResourceNotFoundException {
        PacienteDto paciente = pacienteService.buscarPorId(id);
        return ResponseEntity.ok(paciente);
    }

    @GetMapping(params = "dni")
    public ResponseEntity<PacienteDto> buscar(@RequestParam Integer dni) throws BadRequestException, ResourceNotFoundException {
        PacienteDto paciente = pacienteService.buscar(dni);
        return ResponseEntity.ok(paciente);
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
    public ResponseEntity<?> actualizar(@RequestBody PacienteDto paciente) throws BadRequestException, ResourceNotFoundException {
        PacienteDto actualizado = pacienteService.actualizar(paciente);
        return ResponseEntity.ok(actualizado);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Integer id) throws BadRequestException {
        pacienteService.eliminar(id);
        return ResponseEntity.ok("Se eliminó el paciente con id " + id);
    }

    @Override
    @GetMapping
    public ResponseEntity<List<PacienteDto>> buscarTodos() {
        return ResponseEntity.ok(pacienteService.consultarTodos());
    }
}
