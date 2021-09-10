package com.valva.proyectointegrador.service;

import com.valva.proyectointegrador.dao.configuration.ConfiguracionJDBC;
import com.valva.proyectointegrador.dao.IDao;
import com.valva.proyectointegrador.dao.impl.PacienteDaoH2;
import com.valva.proyectointegrador.model.Paciente;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class PacienteService implements CRUDService<Paciente> {

    @Autowired
    @Qualifier("pacienteDao")
    private IDao<Paciente> pacienteIDao;

    private final Logger logger = Logger.getLogger(PacienteService.class);

    @Override
    public Paciente buscar(Integer id) {
        logger.debug("Iniciando método 'buscar()'");
        Paciente paciente = null;
        try {
            paciente = pacienteIDao.consultarPorId(id);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        logger.debug("Terminó la ejecución del método 'buscar()'");
        return paciente;
    }

    @Override
    public Paciente crear(Paciente paciente) {
        logger.debug("Iniciando método 'crear()'");
        Paciente pacienteInsertado = null;
        try {
            paciente.setFechaIngreso(LocalDate.now());
            pacienteInsertado = pacienteIDao.insertarNuevo(paciente);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        logger.debug("Terminó la ejecución del método 'crear()'");
        return pacienteInsertado;
    }

    @Override
    public Paciente actualizar(Paciente paciente) {
        logger.debug("Iniciando método 'actualizar()'");
        Paciente pacienteActualizado = null;
        try {
            pacienteActualizado = pacienteIDao.actualizar(paciente);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        logger.debug("Terminó la ejecución del método 'actualizar()'");
        return pacienteActualizado;
    }

    @Override
    public void eliminar(Integer id) {
        logger.debug("Iniciando método 'eliminar()'");
        try {
            pacienteIDao.eliminar(id);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        logger.debug("Terminó la ejecución del método 'eliminar()'");
    }

    @Override
    public List<Paciente> consultarTodos() {
        logger.debug("Iniciando método 'consultarTodos()'");
        List<Paciente> pacientes = new ArrayList<>();
        try {
            pacientes = pacienteIDao.consultarTodos();
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        logger.debug("Terminó la ejecución del método 'consultarTodos()'");
        return pacientes;
    }
}
