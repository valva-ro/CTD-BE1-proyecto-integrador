package com.valva.proyectointegrador.controllers.impl;

import com.valva.proyectointegrador.controllers.CRUDController;
import com.valva.proyectointegrador.dto.DomicilioDto;
import com.valva.proyectointegrador.service.IDomicilioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/domicilios")
public class DomicilioController implements CRUDController<DomicilioDto> {

    @Autowired
    @Qualifier("domicilioService")
    private IDomicilioService domicilioService;

    @Override
    @PostMapping()
    public ResponseEntity<DomicilioDto> registrar(@RequestBody DomicilioDto domicilio) {
        ResponseEntity<DomicilioDto> response;
        DomicilioDto domicilioInsertado = domicilioService.crear(domicilio);
        if (domicilioInsertado != null) {
            response = ResponseEntity.ok(domicilioInsertado);
        } else {
            response = ResponseEntity.badRequest().body(domicilio);
        }
        return response;
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<DomicilioDto> buscarPorId(@PathVariable Integer id) {
        ResponseEntity<DomicilioDto> response;
        DomicilioDto domicilio = domicilioService.buscarPorId(id);
        if (domicilio != null) {
            response = ResponseEntity.ok(domicilio);
        } else {
            response = ResponseEntity.notFound().build();
        }
        return response;
    }

    @GetMapping(params = "calle")
    public ResponseEntity<List<DomicilioDto>> buscar(@RequestParam String calle) {
        List<DomicilioDto> domicilios = domicilioService.buscar(calle);
        return ResponseEntity.ok(domicilios);
    }

    @GetMapping(params = {"calle", "numero"})
    public ResponseEntity<List<DomicilioDto>> buscar(@RequestParam String calle, @RequestParam Integer numero) {
        List<DomicilioDto> domicilios = domicilioService.buscar(calle, numero);
        return ResponseEntity.ok(domicilios);
    }

    @GetMapping(params = {"calle", "numero", "localidad", "provincia"})
    public ResponseEntity<DomicilioDto> buscar(@RequestParam String calle, @RequestParam Integer numero, @RequestParam String localidad, @RequestParam String provincia) {
        ResponseEntity<DomicilioDto> response;
        DomicilioDto domicilio = domicilioService.buscar(calle, numero, localidad, provincia);
        if (domicilio != null) {
            response = ResponseEntity.ok(domicilio);
        } else {
            response = ResponseEntity.notFound().build();
        }
        return response;
    }

    @Override
    @PutMapping()
    public ResponseEntity<DomicilioDto> actualizar(@RequestBody DomicilioDto domicilio) {
        ResponseEntity<DomicilioDto> response;
        DomicilioDto actualizado;
        if (domicilio.getId() != null && domicilioService.buscarPorId(domicilio.getId()) != null) {
            actualizado = domicilioService.actualizar(domicilio);
            if (actualizado != null) {
                response = ResponseEntity.ok(actualizado);
            } else {
                response = ResponseEntity.badRequest().body(domicilio);
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
        if (domicilioService.buscarPorId(id) != null) {
            domicilioService.eliminar(id);
            response = ResponseEntity.ok("Se elimino el domicilio con id " + id);
        } else {
            response = ResponseEntity.notFound().build();
        }
        return response;
    }

    @Override
    @GetMapping
    public ResponseEntity<List<DomicilioDto>> buscarTodos() {
        return ResponseEntity.ok(domicilioService.consultarTodos());
    }
}
