package com.valva.proyectointegrador.service.impl;

import com.valva.proyectointegrador.config.SpringConfig;
import com.valva.proyectointegrador.model.DomicilioDto;
import com.valva.proyectointegrador.model.PacienteDto;
import com.valva.proyectointegrador.persistence.entities.Domicilio;
import com.valva.proyectointegrador.persistence.entities.Paciente;
import com.valva.proyectointegrador.persistence.repository.IPacienteRepository;
import com.valva.proyectointegrador.service.IDomicilioService;
import com.valva.proyectointegrador.service.IPacienteService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PacienteService implements IPacienteService {

    @Autowired
    private IPacienteRepository pacienteRepository;
    @Autowired
    private IDomicilioService domicilioService;
    @Autowired
    private SpringConfig springConfig;
    private final Logger logger = Logger.getLogger(PacienteService.class);

    @Override
    public PacienteDto buscar(Integer dni) {
        logger.debug("Iniciando método 'buscar()' por DNI");
        PacienteDto pacienteDto = null;
        Paciente paciente = pacienteRepository.buscar(dni).orElse(null);
        try {
            if (paciente == null)
                throw new Exception("No se encontró el paciente con DNI " + dni);
            pacienteDto = springConfig.getModelMapper().map(paciente, PacienteDto.class);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        logger.debug("Terminó la ejecución del método 'buscar()' por DNI");
        return pacienteDto;
    }

    @Override
    public List<PacienteDto> buscar(String nombre) {
        logger.debug("Iniciando método 'buscar()' por nombre");
        List<PacienteDto> pacientesDto =  new ArrayList<>();
        try {
            List<Paciente> pacientes = pacienteRepository.buscar(nombre).orElse(new ArrayList<>());
            pacientesDto = springConfig.getModelMapper().map(pacientes, pacientesDto.getClass());
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        logger.debug("Terminó la ejecución del método 'buscar()' por nombre");
        return pacientesDto;
    }

    @Override
    public List<PacienteDto> buscar(String nombre, String apellido) {
        logger.debug("Iniciando método 'buscar()' por nombre y apellido");
        List<PacienteDto> pacientesDto =  new ArrayList<>();
        try {
            List<Paciente> pacientes = pacienteRepository.buscar(nombre, apellido).orElse(new ArrayList<>());
            pacientesDto = springConfig.getModelMapper().map(pacientes, pacientesDto.getClass());
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        logger.debug("Terminó la ejecución del método 'buscar()' por nombre y apellido");
        return pacientesDto;
    }

    @Override
    public PacienteDto buscarPorId(Integer id) {
        logger.debug("Iniciando método 'buscarPorId()'");
        PacienteDto pacienteDto = null;
        Paciente paciente = pacienteRepository.findById(id).orElse(null);
        try {
            if (paciente == null)
                throw new Exception("No se encontró el paciente con ID " + id);
            pacienteDto = springConfig.getModelMapper().map(paciente, PacienteDto.class);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        logger.debug("Terminó la ejecución del método 'buscarPorId()'");
        return pacienteDto;
    }

    @Override
    public PacienteDto crear(PacienteDto pacienteDto) {
        logger.debug("Iniciando método 'crear()'");
        try {
            Paciente paciente = springConfig.getModelMapper().map(pacienteDto, Paciente.class);
            pacienteDto = springConfig.getModelMapper().map(pacienteRepository.save(paciente), PacienteDto.class);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        logger.debug("Terminó la ejecución del método 'crear()'");
        return pacienteDto;
    }

    @Override
    public PacienteDto actualizar(PacienteDto pacienteDto) {
        logger.debug("Iniciando método 'actualizar()'");
        PacienteDto pacienteActualizado = null;
        try {
            if (pacienteDto == null || pacienteDto.getId() == null)
                throw new Exception("No se pudo actualizar el paciente " + pacienteDto);
            Optional<Paciente> pacienteEnBD = pacienteRepository.findById(pacienteDto.getId());
            if (pacienteEnBD.isPresent()) {
                Paciente actualizado = this.actualizar(pacienteEnBD.get(), pacienteDto);
                pacienteActualizado = springConfig.getModelMapper().map(pacienteRepository.save(actualizado), PacienteDto.class);
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
    public List<PacienteDto> consultarTodos() {
        logger.debug("Iniciando método 'consultarTodos()'");
        List<PacienteDto> pacientes = new ArrayList<>();
        try {
            pacientes = springConfig.getModelMapper().map(pacienteRepository.findAll(), pacientes.getClass());
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        logger.debug("Terminó la ejecución del método 'consultarTodos()'");
        return pacientes;
    }

    private Paciente actualizar(Paciente paciente, PacienteDto pacienteDto) {
        if (pacienteDto.getNombre() != null) {
            paciente.setNombre(pacienteDto.getNombre());
        }
        if (pacienteDto.getApellido() != null) {
            paciente.setApellido(pacienteDto.getApellido());
        }
        if (pacienteDto.getDni() != null) {
            paciente.setDni(pacienteDto.getDni());
        }
        if (pacienteDto.getDomicilio() != null) {
            DomicilioDto actualizado = domicilioService.actualizar(pacienteDto.getDomicilio());
            paciente.setDomicilio(springConfig.getModelMapper().map(actualizado, Domicilio.class));
        }
        return paciente;
    }
}
