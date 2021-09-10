package com.valva.proyectointegrador.service;

import com.valva.proyectointegrador.dao.IDao;
import com.valva.proyectointegrador.model.Odontologo;
import com.valva.proyectointegrador.model.Paciente;
import com.valva.proyectointegrador.model.Turno;
import com.valva.proyectointegrador.dao.configuration.ConfiguracionJDBC;
import com.valva.proyectointegrador.dao.impl.TurnoDaoH2;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class TurnoService implements CRUDService<Turno> {

    @Autowired
    @Qualifier("odontologoService")
    private CRUDService<Odontologo> odontologoService;

    @Autowired
    @Qualifier("pacienteService")
    private CRUDService<Paciente> pacienteService;

    @Autowired
    @Qualifier("turnoDao")
    private IDao<Turno> turnoIDao;

    private final Logger logger = Logger.getLogger(TurnoService.class);

    @Override
    public Turno buscar(Integer id) {
        logger.debug("Iniciando método 'buscar()'");
        Turno turno = null;
        try {
            turno = turnoIDao.consultarPorId(id);
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
            boolean existeElPaciente = pacienteService.buscar(turno.getIdPaciente()) != null;
            boolean existeElOdontologo = odontologoService.buscar(turno.getIdOdontologo()) != null;
            if (existeElPaciente && existeElOdontologo) {
                turnoInsertado = turnoIDao.insertarNuevo(turno);
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
            turnoActualizado = turnoIDao.actualizar(turno);
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
            turnoIDao.eliminar(id);
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
            turnos = turnoIDao.consultarTodos();
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        logger.debug("Terminó la ejecución del método 'consultarTodos()'");
        return turnos;
    }
}
