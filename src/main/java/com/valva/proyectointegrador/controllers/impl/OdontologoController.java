package com.valva.proyectointegrador.controllers.impl;

import com.valva.proyectointegrador.controllers.CRUDController;
import com.valva.proyectointegrador.exceptions.BadRequestException;
import com.valva.proyectointegrador.exceptions.ResourceNotFoundException;
import com.valva.proyectointegrador.model.OdontologoDto;
import com.valva.proyectointegrador.service.IOdontologoService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/odontologos")
public class OdontologoController implements CRUDController<OdontologoDto> {

    @Qualifier("odontologoService")
    private final IOdontologoService odontologoService;

    public OdontologoController(IOdontologoService odontologoService) {
        this.odontologoService = odontologoService;
    }

    @Override
    @PostMapping()
    public ResponseEntity<?> registrar(@RequestBody OdontologoDto odontologo) throws BadRequestException, ResourceNotFoundException {
        OdontologoDto odontologoInsertado = odontologoService.crear(odontologo);
        return ResponseEntity.ok(odontologoInsertado);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id) throws BadRequestException, ResourceNotFoundException {
        OdontologoDto odontologo = odontologoService.buscarPorId(id);
        return ResponseEntity.ok(odontologo);
    }

    @GetMapping(params = "matricula")
    public ResponseEntity<OdontologoDto> buscar(@RequestParam Integer matricula) throws ResourceNotFoundException, BadRequestException {
        OdontologoDto odontologo = odontologoService.buscar(matricula);
        return ResponseEntity.ok(odontologo);
    }

    @GetMapping(params = "nombre")
    public ResponseEntity<List<OdontologoDto>> buscar(@RequestParam String nombre) {
        List<OdontologoDto> odontologos = odontologoService.buscar(nombre);
        return ResponseEntity.ok(odontologos);
    }

    @GetMapping(params = {"nombre", "apellido"})
    public ResponseEntity<List<OdontologoDto>> buscar(@RequestParam String nombre, @RequestParam String apellido) {
        List<OdontologoDto> odontologos = odontologoService.buscar(nombre, apellido);
        return ResponseEntity.ok(odontologos);
    }

    @Override
    @PutMapping()
    public ResponseEntity<?> actualizar(@RequestBody OdontologoDto odontologo) throws BadRequestException, ResourceNotFoundException {
        OdontologoDto actualizado = odontologoService.actualizar(odontologo);
        return ResponseEntity.ok(actualizado);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Integer id) throws BadRequestException {
        odontologoService.eliminar(id);
        return ResponseEntity.ok("Se eliminó el odontólogo con id " + id);
    }

    @Override
    @GetMapping
    public ResponseEntity<List<OdontologoDto>> buscarTodos(){
        return ResponseEntity.ok(odontologoService.consultarTodos());
    }
}
