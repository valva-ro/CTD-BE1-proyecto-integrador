package com.valva.proyectointegrador.controllers.impl;

import com.valva.proyectointegrador.controllers.ITurnoController;
import com.valva.proyectointegrador.exceptions.BadRequestException;
import com.valva.proyectointegrador.exceptions.ResourceNotFoundException;
import com.valva.proyectointegrador.model.TurnoDto;
import com.valva.proyectointegrador.service.ITurnoService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/turnos")
public class TurnoController implements ITurnoController {

    @Qualifier("turnoService")
    private final ITurnoService turnoService;

    public TurnoController(ITurnoService turnoService) {
        this.turnoService = turnoService;
    }

    @Override
    @PostMapping()
    public ResponseEntity<?> registrar(@RequestBody TurnoDto turno) throws BadRequestException, ResourceNotFoundException {
        TurnoDto turnoInsertado = turnoService.crear(turno);
        return ResponseEntity.ok(turnoInsertado);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id) throws BadRequestException, ResourceNotFoundException {
        TurnoDto turno = turnoService.buscarPorId(id);
        return ResponseEntity.ok(turno);
    }

    @GetMapping(params = {"nombrePaciente", "apellidoPaciente", "nombreOdontologo", "apellidoOdontologo"})
    public ResponseEntity<List<TurnoDto>> buscar(String nombrePaciente, String apellidoPaciente, String nombreOdontologo, String apellidoOdontologo) {
        List<TurnoDto> turnos = turnoService.buscar(nombrePaciente, apellidoPaciente, nombreOdontologo, apellidoOdontologo);
        return ResponseEntity.ok(turnos);
    }

    @GetMapping(params = {"nombreOdontologo", "apellidoOdontologo"})
    public ResponseEntity<List<TurnoDto>> buscar(String nombreOdontologo, String apellidoOdontologo) {
        List<TurnoDto> turnos = turnoService.buscar(nombreOdontologo, apellidoOdontologo);
        return ResponseEntity.ok(turnos);
    }

    @GetMapping(params = {"matricula", "dni"})
    public ResponseEntity<List<TurnoDto>> buscar(Integer matricula, Integer dni) {
        List<TurnoDto> turnos = turnoService.buscar(matricula, dni);
        return ResponseEntity.ok(turnos);
    }

    @Override
    @PutMapping()
    public ResponseEntity<?> actualizar(@RequestBody TurnoDto turno) throws BadRequestException, ResourceNotFoundException {
        TurnoDto actualizado = turnoService.actualizar(turno);
        return ResponseEntity.ok(actualizado);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Integer id) throws BadRequestException {
        turnoService.eliminar(id);
        return ResponseEntity.ok("Se elimino el turno con id " + id);
    }

    @Override
    @GetMapping
    public ResponseEntity<List<TurnoDto>> buscarTodos() {
        return ResponseEntity.ok(turnoService.consultarTodos());
    }

    @Override
    @GetMapping("/proximos")
    public ResponseEntity<List<TurnoDto>> buscarTurnosProximaSemana() {
        return ResponseEntity.ok(turnoService.consultarTurnosProximaSemana());
    }
}
