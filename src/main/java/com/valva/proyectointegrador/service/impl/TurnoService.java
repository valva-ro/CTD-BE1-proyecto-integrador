package com.valva.proyectointegrador.service.impl;

import com.valva.proyectointegrador.model.Odontologo;
import com.valva.proyectointegrador.model.Paciente;
import com.valva.proyectointegrador.model.Turno;
import com.valva.proyectointegrador.repository.ITurnoRepository;
import com.valva.proyectointegrador.service.CRUDService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TurnoService implements CRUDService<Turno> {

    @Autowired
    private CRUDService<Odontologo> odontologoService;

    @Autowired
    private CRUDService<Paciente> pacienteService;

    @Autowired
    private ITurnoRepository turnoRepository;

    private final Logger logger = Logger.getLogger(TurnoService.class);

    @Override
    public Turno buscar(Integer id) {
        logger.debug("Iniciando método 'buscar()'");
        Turno turno = null;
        try {
            if (turnoRepository.findById(id).isPresent())
                turno = turnoRepository.findById(id).get();
            else
                throw new Exception("No se encontró el turno con id " + id);

        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        logger.debug("Terminó la ejecución del método 'buscar()'");
        return turno;
    }

    @Override
    public Turno crear(Turno turno) {
        logger.debug("Iniciando método 'crear()'");
        Turno turnoInsertado = null;
        try {
            Paciente p = pacienteService.buscar(turno.getPaciente().getId());
            Odontologo o = odontologoService.buscar(turno.getOdontologo().getId());
            boolean existeElPaciente = (p != null);
            boolean existeElOdontologo = (o != null);
            if (existeElPaciente && existeElOdontologo) {
                turno.setPaciente(p);
                turno.setOdontologo(o);
                turnoInsertado = turnoRepository.save(turno);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        logger.debug("Terminó la ejecución del método 'crear()'");
        return turnoInsertado;
    }

    @Override
    public Turno actualizar(Turno turno) {
        logger.debug("Iniciando método 'actualizar()'");
        Turno turnoActualizado = null;
        try {
            if (turnoRepository.existsById(turno.getId()))
                turnoActualizado = turnoRepository.save(turno);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        logger.debug("Terminó la ejecución del método 'actualizar()'");
        return turnoActualizado;
    }

    @Override
    public void eliminar(Integer id) {
        logger.debug("Iniciando método 'eliminar()'");
        try {
            turnoRepository.deleteById(id);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        logger.debug("Terminó la ejecución del método 'eliminar()'");
    }

    @Override
    public List<Turno> consultarTodos() {
        logger.debug("Iniciando método 'consultarTodos()'");
        List<Turno> turnos = new ArrayList<>();
        try {
            turnos = turnoRepository.findAll();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        logger.debug("Terminó la ejecución del método 'consultarTodos()'");
        return turnos;
    }
}
