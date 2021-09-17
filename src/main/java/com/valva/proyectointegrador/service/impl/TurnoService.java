package com.valva.proyectointegrador.service.impl;

import com.valva.proyectointegrador.persistence.model.Odontologo;
import com.valva.proyectointegrador.persistence.model.Paciente;
import com.valva.proyectointegrador.persistence.model.Turno;
import com.valva.proyectointegrador.persistence.repository.ITurnoRepository;
import com.valva.proyectointegrador.service.CRUDService;
import com.valva.proyectointegrador.service.ITurnoService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TurnoService implements ITurnoService {

    @Autowired
    private CRUDService<Odontologo> odontologoService;

    @Autowired
    private CRUDService<Paciente> pacienteService;

    @Autowired
    private ITurnoRepository turnoRepository;

    private final Logger logger = Logger.getLogger(TurnoService.class);

    @Override
    public List<Turno> buscar(String nombrePaciente, String apellidoPaciente, String nombreOdontologo, String apellidoOdontologo) {
        logger.debug("Iniciando método 'buscar()' por nombres y apellidos");
        List<Turno> turnos = null;
        try {
            turnos = turnoRepository.buscar(nombrePaciente, apellidoPaciente, nombreOdontologo, apellidoOdontologo).orElse(new ArrayList<>());
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        logger.debug("Terminó la ejecución del método 'buscar()' por nombres y apellidos");
        return turnos;
    }

    @Override
    public List<Turno> buscar(String nombreOdontologo, String apellidoOdontologo) {
        logger.debug("Iniciando método 'buscar()' por nombre y apellido del odontologo");
        List<Turno> turnos = null;
        try {
            turnos = turnoRepository.buscar(nombreOdontologo, apellidoOdontologo).orElse(new ArrayList<>());
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        logger.debug("Terminó la ejecución del método 'buscar()' por nombre y apellido del odontologo");
        return turnos;
    }

    @Override
    public List<Turno> buscar(Integer matricula, Integer dni) {
        logger.debug("Iniciando método 'buscar()' por matricula de odontologo y dni de paciente");
        List<Turno> turnos = null;
        try {
            turnos = turnoRepository.buscar(matricula, dni).orElse(new ArrayList<>());
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        logger.debug("Terminó la ejecución del método 'buscar()' por matricula de odontologo y dni de paciente");
        return turnos;
    }

    @Override
    public Turno buscarPorId(Integer id) {
        logger.debug("Iniciando método 'buscarPorId()'");
        Turno turno = null;
        try {
            if (turnoRepository.findById(id).isPresent())
                turno = turnoRepository.findById(id).get();
            else
                throw new Exception("No se encontró el turno con id " + id);

        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        logger.debug("Terminó la ejecución del método 'buscarPorId()'");
        return turno;
    }

    @Override
    public Turno crear(Turno turno) {
        logger.debug("Iniciando método 'crear()'");
        Turno turnoInsertado = null;
        try {
            Paciente p = pacienteService.buscarPorId(turno.getPaciente().getId());
            Odontologo o = odontologoService.buscarPorId(turno.getOdontologo().getId());
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
