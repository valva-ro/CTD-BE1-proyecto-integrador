package com.valva.proyectointegrador.service;

import com.valva.proyectointegrador.model.Turno;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class TurnoService {

    @Autowired
    public OdontologoService odontologoService;
    @Autowired
    public PacienteService pacienteService;

    private List<Turno> turnos = new ArrayList<>();
    private Logger logger = Logger.getLogger(TurnoService.class);

    public Turno buscar(Integer id) {
        logger.debug("Iniciando método 'buscar()'");
        Turno turno = null;
        try {
            for (Turno t : turnos) {
                if (Objects.equals(t.getId(), id)) {
                    turno = t;
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        logger.debug("Terminó la ejecución del método 'buscar()'");
        return turno;
    }

    public Turno crear(Turno turno) {
        logger.debug("Iniciando método 'crear()'");
        Integer idPaciente = turno.getPaciente().getId();
        Integer idOdontologo = turno.getOdontologo().getId();
        Turno turnoInsertado = null;
        try {
            Boolean existenEntidades = existePaciente(idPaciente) && existeOdontologo(idOdontologo);
            if (existenEntidades) {
                turno.setPaciente(pacienteService.buscar(idPaciente));
                turno.setOdontologo(odontologoService.buscar(idOdontologo));
                turno.setId(turnos.size() + 1);
                turnos.add(turno);
                turnoInsertado = turno;
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        logger.debug("Terminó la ejecución del método 'crear()'");
        return turnoInsertado;
    }

    public Turno actualizar(Turno turno) {
        logger.debug("Iniciando método 'actualizar()'");
        Turno turnoActualizado = turno;
        try {
            turnos.set(turno.getId() - 1, turno);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        logger.debug("Terminó la ejecución del método 'actualizar()'");
        return turnoActualizado;
    }

    public void eliminar(Integer id) {
        logger.debug("Iniciando método 'eliminar()'");
        try {
            turnos.remove(buscar(id));
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        logger.debug("Terminó la ejecución del método 'eliminar()'");
    }

    public List<Turno> consultarTodos() {
        logger.debug("Iniciando método 'consultarTodos()'");
        logger.debug("Terminó la ejecución del método 'consultarTodos()'");
        return turnos;
    }

    private Boolean existePaciente(Integer id) {
        return pacienteService.buscar(id) != null;
    }

    private Boolean existeOdontologo(Integer id) {
        return odontologoService.buscar(id) != null;
    }
}
