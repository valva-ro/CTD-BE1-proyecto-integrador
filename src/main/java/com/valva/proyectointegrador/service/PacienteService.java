package com.valva.proyectointegrador.service;

import com.valva.proyectointegrador.repository.configuration.ConfiguracionJDBC;
import com.valva.proyectointegrador.repository.IRepository;
import com.valva.proyectointegrador.repository.impl.PacienteRepositoryH2;
import com.valva.proyectointegrador.model.Paciente;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class PacienteService implements CRUDService<Paciente> {

    private IRepository<Paciente> pacienteIRepository;
    private Logger logger = Logger.getLogger(PacienteService.class);

    public PacienteService() throws Exception {
        this.pacienteIRepository = new PacienteRepositoryH2(new ConfiguracionJDBC());
    }

    public PacienteService(ConfiguracionJDBC configuracionJDBC) {
        try {
            this.pacienteIRepository = new PacienteRepositoryH2(configuracionJDBC);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    public Paciente buscar(Integer id) {
        logger.debug("Iniciando método 'buscar()'");
        Paciente paciente = null;
        try {
            paciente = pacienteIRepository.consultarPorId(id);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        logger.debug("Terminó la ejecución del método 'buscar()'");
        return paciente;
    }

    public Paciente crear(Paciente paciente) {
        logger.debug("Iniciando método 'crear()'");
        Paciente pacienteInsertado = null;
        try {
            paciente.setFechaIngreso(LocalDate.now());
            pacienteInsertado = pacienteIRepository.insertarNuevo(paciente);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        logger.debug("Terminó la ejecución del método 'crear()'");
        return pacienteInsertado;
    }

    public Paciente actualizar(Paciente paciente) {
        logger.debug("Iniciando método 'actualizar()'");
        Paciente pacienteActualizado = null;
        try {
            pacienteActualizado = pacienteIRepository.actualizar(paciente);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        logger.debug("Terminó la ejecución del método 'actualizar()'");
        return pacienteActualizado;
    }

    public void eliminar(Integer id) {
        logger.debug("Iniciando método 'eliminar()'");
        try {
            pacienteIRepository.eliminar(id);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        logger.debug("Terminó la ejecución del método 'eliminar()'");
    }

    public List<Paciente> consultarTodos() {
        logger.debug("Iniciando método 'consultarTodos()'");
        List<Paciente> pacientes = new ArrayList<>();
        try {
            pacientes = pacienteIRepository.consultarTodos();
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        logger.debug("Terminó la ejecución del método 'consultarTodos()'");
        return pacientes;
    }
}
