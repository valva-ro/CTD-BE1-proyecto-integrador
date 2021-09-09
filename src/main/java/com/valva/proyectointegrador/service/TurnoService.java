package com.valva.proyectointegrador.service;

import com.valva.proyectointegrador.model.Turno;
import com.valva.proyectointegrador.repository.configuration.ConfiguracionJDBC;
import com.valva.proyectointegrador.repository.impl.TurnoRepositoryH2;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class TurnoService implements CRUDService<Turno> {

    @Autowired private OdontologoService odontologoService;
    @Autowired private PacienteService pacienteService;
    private TurnoRepositoryH2 turnoRepositoryH2;
    private Logger logger = Logger.getLogger(TurnoService.class);


    public TurnoService() throws Exception {
        this.turnoRepositoryH2 = new TurnoRepositoryH2(new ConfiguracionJDBC());
    }

    public TurnoService(ConfiguracionJDBC configuracionJDBC) {
        try {
            this.turnoRepositoryH2 = new TurnoRepositoryH2(configuracionJDBC);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public Turno buscar(Integer id) {
        logger.debug("Iniciando método 'buscar()'");
        Turno turno = null;
        try {
            turno = turnoRepositoryH2.consultarPorId(id);
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
                turnoInsertado = turnoRepositoryH2.insertarNuevo(turno);
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
            turnoActualizado = turnoRepositoryH2.actualizar(turno);
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
            turnoRepositoryH2.eliminar(id);
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
            turnos = turnoRepositoryH2.consultarTodos();
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        logger.debug("Terminó la ejecución del método 'consultarTodos()'");
        return turnos;
    }
}
