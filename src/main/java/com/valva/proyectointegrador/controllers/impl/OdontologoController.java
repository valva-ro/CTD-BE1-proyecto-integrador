package com.valva.proyectointegrador.controllers.impl;

import com.valva.proyectointegrador.controllers.CRUDController;
import com.valva.proyectointegrador.exceptions.service.OdontologoServiceException;
import com.valva.proyectointegrador.model.OdontologoDto;
import com.valva.proyectointegrador.service.IOdontologoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/odontologos")
public class OdontologoController implements CRUDController<OdontologoDto> {

    @Autowired
    @Qualifier("odontologoService")
    private IOdontologoService odontologoService;

    @Override
    @PostMapping()
    public ResponseEntity<?> registrar(@RequestBody OdontologoDto odontologo) {
        ResponseEntity<?> response;
        try {
            OdontologoDto odontologoInsertado = odontologoService.crear(odontologo);
            response = ResponseEntity.ok(odontologoInsertado);
        } catch (Exception e) {
            response = ResponseEntity.badRequest().body(e.getMessage());
        }
        return response;
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id) {
        ResponseEntity<?> response;
        OdontologoDto odontologo = null;
        try {
            odontologo = odontologoService.buscarPorId(id);
            if (odontologo != null)
                response = ResponseEntity.ok(odontologo);
            else
                response = ResponseEntity.notFound().build();
        } catch (Exception e) {
            response = ResponseEntity.badRequest().body(e.getMessage());
        }
        return response;
    }

    @GetMapping(params = "matricula")
    public ResponseEntity<OdontologoDto> buscar(@RequestParam Integer matricula) {
        ResponseEntity<OdontologoDto> response;
        try {
            OdontologoDto odontologo = odontologoService.buscar(matricula);
            response = ResponseEntity.ok(odontologo);
        } catch (OdontologoServiceException e) {
            response = ResponseEntity.notFound().build();
        }
        return response;
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
    public ResponseEntity<?> actualizar(@RequestBody OdontologoDto odontologo) {
        ResponseEntity<?> response;
        OdontologoDto actualizado;
        try {
            actualizado = odontologoService.actualizar(odontologo);
            response = ResponseEntity.ok(actualizado);
        } catch (Exception e) {
            response = ResponseEntity.badRequest().body(e.getMessage());
        }
        return response;
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Integer id) {
        odontologoService.eliminar(id);
        return ResponseEntity.ok("Se elimino el odontologo con id " + id);
    }

    @Override
    @GetMapping
    public ResponseEntity<List<OdontologoDto>> buscarTodos(){
        return ResponseEntity.ok(odontologoService.consultarTodos());
    }
}
