package com.valva.proyectointegrador.controllers.impl;

import com.valva.proyectointegrador.controllers.CRUDController;
import com.valva.proyectointegrador.model.Domicilio;
import com.valva.proyectointegrador.service.IDomicilioService;
import com.valva.proyectointegrador.service.impl.DomicilioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/domicilios")
public class DomicilioController implements CRUDController<Domicilio> {

    @Autowired
    @Qualifier("domicilioService")
    private IDomicilioService domicilioService;

    @Override
    @PostMapping()
    public ResponseEntity<Domicilio> registrar(@RequestBody Domicilio domicilio) {
        ResponseEntity<Domicilio> response;
        Domicilio domicilioInsertado = domicilioService.crear(domicilio);
        if (domicilioInsertado != null) {
            response = ResponseEntity.ok(domicilioInsertado);
        } else {
            response = ResponseEntity.badRequest().body(domicilio);
        }
        return response;
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<Domicilio> buscarPorId(@PathVariable Integer id) {
        ResponseEntity<Domicilio> response;
        Domicilio domicilio = domicilioService.buscarPorId(id);
        if (domicilio != null) {
            response = ResponseEntity.ok(domicilio);
        } else {
            response = ResponseEntity.notFound().build();
        }
        return response;
    }

    @GetMapping(params = "calle")
    public ResponseEntity<List<Domicilio>> buscar(@RequestParam String calle) {
        List<Domicilio> domicilios = domicilioService.buscar(calle);
        return ResponseEntity.ok(domicilios);
    }

    @GetMapping(params = {"calle", "numero"})
    public ResponseEntity<List<Domicilio>> buscar(@RequestParam String calle, @RequestParam Integer numero) {
        List<Domicilio> domicilios = domicilioService.buscar(calle, numero);
        return ResponseEntity.ok(domicilios);
    }

    @GetMapping(params = {"calle", "numero", "localidad", "provincia"})
    public ResponseEntity<Domicilio> buscar(@RequestParam String calle, @RequestParam Integer numero, @RequestParam String localidad, @RequestParam String provincia) {
        ResponseEntity<Domicilio> response;
        Domicilio domicilio = domicilioService.buscar(calle, numero, localidad, provincia);
        if (domicilio != null) {
            response = ResponseEntity.ok(domicilio);
        } else {
            response = ResponseEntity.notFound().build();
        }
        return response;
    }

    @Override
    @PutMapping()
    public ResponseEntity<Domicilio> actualizar(@RequestBody Domicilio domicilio) {
        ResponseEntity<Domicilio> response;
        Domicilio actualizado;
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
    public ResponseEntity<List<Domicilio>> buscarTodos() {
        return ResponseEntity.ok(domicilioService.consultarTodos());
    }
}
