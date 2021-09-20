package com.valva.proyectointegrador.controllers.impl;

import com.valva.proyectointegrador.controllers.CRUDController;
import com.valva.proyectointegrador.model.TurnoDto;
import com.valva.proyectointegrador.service.ITurnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/turnos")
public class TurnoController implements CRUDController<TurnoDto> {

    @Autowired
    @Qualifier("turnoService")
    private ITurnoService turnoService;

    @Override
    @PostMapping()
    public ResponseEntity<?> registrar(@RequestBody TurnoDto turno) {
        ResponseEntity<?> response;
        try {
            TurnoDto turnoInsertado = turnoService.crear(turno);
            response = ResponseEntity.ok(turnoInsertado);
        } catch (Exception e) {
            response = ResponseEntity.badRequest().body(e.getMessage());
        }
        return response;
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id) {
        ResponseEntity<?> response;
        try {
            TurnoDto turno = turnoService.buscarPorId(id);
            if (turno != null) {
                response = ResponseEntity.ok(turno);
            } else {
                response = ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            response = ResponseEntity.badRequest().body(e.getMessage());
        }
        return response;
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
    public ResponseEntity<?> actualizar(@RequestBody TurnoDto turno) {
        ResponseEntity<?> response;
        try {
            TurnoDto actualizado = turnoService.actualizar(turno);
            response = ResponseEntity.ok(actualizado);
        } catch (Exception e) {
            response = ResponseEntity.badRequest().body(e.getMessage());
        }
        return response;
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Integer id) {
        turnoService.eliminar(id);
        return ResponseEntity.ok("Se elimino el turno con id " + id);
    }

    @Override
    @GetMapping
    public ResponseEntity<List<TurnoDto>> buscarTodos() {
        return ResponseEntity.ok(turnoService.consultarTodos());
    }
}
