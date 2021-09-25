package com.valva.proyectointegrador.controllers;

import com.valva.proyectointegrador.model.TurnoDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ITurnoController extends CRUDController<TurnoDto> {
    ResponseEntity<List<TurnoDto>> buscarTurnosProximaSemana();
}
