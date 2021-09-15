package com.valva.proyectointegrador.service.impl;

import com.valva.proyectointegrador.model.Paciente;
import com.valva.proyectointegrador.repository.IPacienteRepository;
import com.valva.proyectointegrador.service.IPacienteService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class PacienteService implements IPacienteService {

    @Autowired
    private IPacienteRepository pacienteRepository;

    private final Logger logger = Logger.getLogger(PacienteService.class);

    @Override
    public Paciente buscar(Integer dni) {
        logger.debug("Iniciando método 'buscar()' por DNI");
        Paciente paciente = null;
        try {
            paciente = pacienteRepository.buscar(dni).get();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        logger.debug("Terminó la ejecución del método 'buscar()' por DNI");
        return paciente;
    }

    @Override
    public List<Paciente> buscar(String nombre) {
        logger.debug("Iniciando método 'buscar()' por nombre");
        List<Paciente> pacientes = null;
        try {
            pacientes = pacienteRepository.buscar(nombre).orElse(new ArrayList<>());
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        logger.debug("Terminó la ejecución del método 'buscar()' por nombre");
        return pacientes;
    }

    @Override
    public List<Paciente> buscar(String nombre, String apellido) {
        logger.debug("Iniciando método 'buscar()' por nombre y apellido");
        List<Paciente> pacientes = null;
        try {
            pacientes = pacienteRepository.buscar(nombre, apellido).orElse(new ArrayList<>());
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        logger.debug("Terminó la ejecución del método 'buscar()' por nombre y apellido");
        return pacientes;
    }

    @Override
    public Paciente buscarPorId(Integer id) {
        logger.debug("Iniciando método 'buscarPorId()'");
        Paciente paciente = null;
        try {
            if (pacienteRepository.findById(id).isPresent())
                paciente = pacienteRepository.findById(id).get();
            else
                throw new Exception("No se encontró el paciente con id " + id);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        logger.debug("Terminó la ejecución del método 'buscarPorId()'");
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
            Paciente pacienteEnBD = pacienteRepository.findById(paciente.getId()).orElse(null);
            if (pacienteEnBD != null) {
                paciente.setFechaIngreso(pacienteEnBD.getFechaIngreso());
                pacienteActualizado = pacienteRepository.save(paciente);
            }
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
