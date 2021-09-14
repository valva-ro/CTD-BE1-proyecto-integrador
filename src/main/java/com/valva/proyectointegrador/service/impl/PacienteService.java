package com.valva.proyectointegrador.service.impl;

import com.valva.proyectointegrador.model.Paciente;
import com.valva.proyectointegrador.repository.IPacienteRepository;
import com.valva.proyectointegrador.service.CRUDService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class PacienteService implements CRUDService<Paciente> {

    @Autowired
    private IPacienteRepository pacienteRepository;

    private final Logger logger = Logger.getLogger(PacienteService.class);

    @Override
    public Paciente buscar(Integer id) {
        logger.debug("Iniciando método 'buscar()'");
        Paciente paciente = null;
        try {
            if (pacienteRepository.findById(id).isPresent())
                paciente = pacienteRepository.findById(id).get();
            else
                throw new Exception("No se encontró el paciente con id " + id);
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
            pacienteInsertado = pacienteRepository.save(paciente);
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
            if (pacienteRepository.existsById(paciente.getId()))
                pacienteActualizado = pacienteRepository.save(paciente);
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
            pacienteRepository.deleteById(id);
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
            pacientes = pacienteRepository.findAll();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        logger.debug("Terminó la ejecución del método 'consultarTodos()'");
        return pacientes;
    }
}
