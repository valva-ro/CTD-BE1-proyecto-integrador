package com.valva.proyectointegrador.controllers.impl;

import com.valva.proyectointegrador.controllers.CRUDController;
import com.valva.proyectointegrador.model.Turno;
import com.valva.proyectointegrador.service.ITurnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/turnos")
public class TurnoController implements CRUDController<Turno> {

    @Autowired
    @Qualifier("turnoService")
    private ITurnoService turnoService;

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
    public ResponseEntity<Turno> buscarPorId(@PathVariable Integer id) {
        ResponseEntity<Turno> response;
        Turno turno = turnoService.buscarPorId(id);
        if (turno != null) {
            response = ResponseEntity.ok(turno);
        } else {
            response = ResponseEntity.notFound().build();
        }
        return response;
    }

    @GetMapping(params = {"nombrePaciente", "apellidoPaciente", "nombreOdontologo", "apellidoOdontologo"})
    public ResponseEntity<List<Turno>> buscar(String nombrePaciente, String apellidoPaciente, String nombreOdontologo, String apellidoOdontologo) {
        List<Turno> turnos = turnoService.buscar(nombrePaciente, apellidoPaciente, nombreOdontologo, apellidoOdontologo);
        return ResponseEntity.ok(turnos);
    }

    @GetMapping(params = {"nombreOdontologo", "apellidoOdontologo"})
    public ResponseEntity<List<Turno>> buscar(String nombreOdontologo, String apellidoOdontologo) {
        List<Turno> turnos = turnoService.buscar(nombreOdontologo, apellidoOdontologo);
        return ResponseEntity.ok(turnos);
    }

    @GetMapping(params = {"matricula", "dni"})
    public ResponseEntity<List<Turno>> buscar(Integer matricula, Integer dni) {
        List<Turno> turnos = turnoService.buscar(matricula, dni);
        return ResponseEntity.ok(turnos);
    }

    @Override
    @PutMapping()
    public ResponseEntity<Turno> actualizar(@RequestBody Turno turno) {
        ResponseEntity<Turno> response;
        if (turno.getId() != null && turnoService.buscarPorId(turno.getId()) != null) {
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
        if (turnoService.buscarPorId(id) != null) {
            turnoService.eliminar(id);
            response = ResponseEntity.ok("Se elimino el turno con id " + id);
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
