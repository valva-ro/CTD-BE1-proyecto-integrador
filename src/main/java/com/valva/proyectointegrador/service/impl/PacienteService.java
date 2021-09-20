package com.valva.proyectointegrador.service.impl;

import com.valva.proyectointegrador.config.SpringConfig;
import com.valva.proyectointegrador.exceptions.service.PacienteServiceException;
import com.valva.proyectointegrador.model.DomicilioDto;
import com.valva.proyectointegrador.model.PacienteDto;
import com.valva.proyectointegrador.persistence.entities.Domicilio;
import com.valva.proyectointegrador.persistence.entities.Paciente;
import com.valva.proyectointegrador.persistence.repository.IPacienteRepository;
import com.valva.proyectointegrador.service.IDomicilioService;
import com.valva.proyectointegrador.service.IPacienteService;
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

    @Override
    public PacienteDto buscar(Integer dni) throws PacienteServiceException {
        Paciente paciente = pacienteRepository.buscar(dni).orElse(null);
        if (paciente == null)
            throw new PacienteServiceException("No se encontró el paciente con DNI " + dni);
        return springConfig.getModelMapper().map(paciente, PacienteDto.class);
    }

    @Override
    public List<PacienteDto> buscar(String nombre) {
        List<Paciente> pacientes = pacienteRepository.buscar(nombre).orElse(new ArrayList<>());
        return springConfig.getModelMapper().map(pacientes, List.class);
    }

    @Override
    public List<PacienteDto> buscar(String nombre, String apellido) {
        List<Paciente> pacientes = pacienteRepository.buscar(nombre, apellido).orElse(new ArrayList<>());
        return springConfig.getModelMapper().map(pacientes, List.class);
    }

    @Override
    public PacienteDto buscarPorId(Integer id) throws PacienteServiceException {
        Paciente paciente = pacienteRepository.findById(id).orElse(null);
        if (paciente == null)
            throw new PacienteServiceException("No se encontró el paciente con ID " + id);
        return springConfig.getModelMapper().map(paciente, PacienteDto.class);
    }

    @Override
    public PacienteDto crear(PacienteDto pacienteDto) throws PacienteServiceException {
        if (pacienteDto == null)
            throw new PacienteServiceException("No se puede crear un paciente null");
        Paciente paciente = springConfig.getModelMapper().map(pacienteDto, Paciente.class);
        return springConfig.getModelMapper().map(pacienteRepository.save(paciente), PacienteDto.class);
    }

    @Override
    public PacienteDto actualizar(PacienteDto pacienteDto) throws Exception {
        if (pacienteDto == null)
            throw new PacienteServiceException("No se pudo actualizar el paciente null");
        if (pacienteDto.getId() == null)
            throw new PacienteServiceException("El id del paciente a actualizar no puede ser null");
        Optional<Paciente> pacienteEnBD = pacienteRepository.findById(pacienteDto.getId());
        if (pacienteEnBD.isPresent()) {
            Paciente actualizado = this.actualizar(pacienteEnBD.get(), pacienteDto);
            pacienteDto = springConfig.getModelMapper().map(pacienteRepository.save(actualizado), PacienteDto.class);
        } else {
            throw new PacienteServiceException("El paciente no existe");
        }
        return pacienteDto;
    }

    @Override
    public void eliminar(Integer id) {
        pacienteRepository.deleteById(id);
    }

    @Override
    public List<PacienteDto> consultarTodos() {
        return springConfig.getModelMapper().map(pacienteRepository.findAll(), List.class);
    }

    private Paciente actualizar(Paciente paciente, PacienteDto pacienteDto) throws Exception {
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
