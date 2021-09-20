package com.valva.proyectointegrador.service.impl;

import com.valva.proyectointegrador.config.SpringConfig;
import com.valva.proyectointegrador.exceptions.service.TurnoServiceException;
import com.valva.proyectointegrador.model.OdontologoDto;
import com.valva.proyectointegrador.model.PacienteDto;
import com.valva.proyectointegrador.model.TurnoDto;
import com.valva.proyectointegrador.persistence.entities.Turno;
import com.valva.proyectointegrador.persistence.repository.ITurnoRepository;
import com.valva.proyectointegrador.service.CRUDService;
import com.valva.proyectointegrador.service.ITurnoService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TurnoService implements ITurnoService {

    @Autowired
    private CRUDService<OdontologoDto> odontologoService;
    @Autowired
    private CRUDService<PacienteDto> pacienteService;
    @Autowired
    private ITurnoRepository turnoRepository;
    @Autowired
    private SpringConfig springConfig;

    @Override
    public List<TurnoDto> buscar(String nombrePaciente, String apellidoPaciente, String nombreOdontologo, String apellidoOdontologo) {
        List<Turno> turnos = turnoRepository.buscar(nombrePaciente, apellidoPaciente, nombreOdontologo, apellidoOdontologo).orElse(new ArrayList<>());
        return springConfig.getModelMapper().map(turnos, List.class);
    }

    @Override
    public List<TurnoDto> buscar(String nombreOdontologo, String apellidoOdontologo) {
        List<Turno> turnos = turnoRepository.buscar(nombreOdontologo, apellidoOdontologo).orElse(new ArrayList<>());
        return springConfig.getModelMapper().map(turnos, List.class);
    }

    @Override
    public List<TurnoDto> buscar(Integer matricula, Integer dni) {
        List<Turno> turnos = turnoRepository.buscar(matricula, dni).orElse(new ArrayList<>());
        return springConfig.getModelMapper().map(turnos, List.class);
    }

    @Override
    public TurnoDto buscarPorId(Integer id) throws TurnoServiceException {
        Turno turno = turnoRepository.findById(id).orElse(null);
        if (turno == null)
            throw new TurnoServiceException("No se encontró el turno con id " + id);
        return springConfig.getModelMapper().map(turno, TurnoDto.class);
    }

    @Override
    public TurnoDto crear(TurnoDto turnoDto) throws Exception {
        Integer pacienteId = turnoDto.getPaciente().getId();
        Integer odontologoId = turnoDto.getOdontologo().getId();
        if (this.existenPacienteYOdontologo(pacienteId, odontologoId)) {
            Turno turno = springConfig.getModelMapper().map(turnoDto, Turno.class);
            turnoDto = springConfig.getModelMapper().map(turnoRepository.save(turno), TurnoDto.class);
            turnoDto.setPaciente(pacienteService.buscarPorId(pacienteId));
            turnoDto.setOdontologo(odontologoService.buscarPorId(odontologoId));
        } else {
            throw new TurnoServiceException("El paciente u odontologo no existen");
        }
        return turnoDto;
    }

    @SneakyThrows
    @Override
    public TurnoDto actualizar(TurnoDto turnoDto) {
        TurnoDto turnoActualizado = null;
        if (turnoDto == null)
            throw new TurnoServiceException("No se pudo actualizar el turno null");
        if (turnoDto.getId() == null)
            throw new TurnoServiceException("El id del turno a actualizar no puede ser null");
        Optional<Turno> turnoEnBD = turnoRepository.findById(turnoDto.getId());
        if (turnoEnBD.isPresent()) {
            Turno actualizado = this.actualizar(turnoEnBD.get(), turnoDto);
            Turno guardado = turnoRepository.save(actualizado);
            turnoActualizado = springConfig.getModelMapper().map(guardado, TurnoDto.class);
        } else {
            throw new TurnoServiceException("El odontologo no existe");
        }
        return turnoActualizado;
    }

    @Override
    public void eliminar(Integer id) {
        turnoRepository.deleteById(id);
    }

    @Override
    public List<TurnoDto> consultarTodos() {
        List<Turno> turnos = turnoRepository.findAll();
        return springConfig.getModelMapper().map(turnos, List.class);
    }

    private Turno actualizar(Turno turno, TurnoDto turnoDto) throws Exception {
        if (turnoDto.getFecha() != null) {
            turno.setFecha(turnoDto.getFecha());
        }
        if (turnoDto.getPaciente() != null) {
            pacienteService.actualizar(turnoDto.getPaciente());
        }
        if (turnoDto.getOdontologo() != null) {
            odontologoService.actualizar(turnoDto.getOdontologo());
        }
        return turno;
    }

    private boolean existenPacienteYOdontologo(Integer pacienteId, Integer odontologoId) throws Exception {
        PacienteDto p = pacienteService.buscarPorId(pacienteId);
        OdontologoDto o = odontologoService.buscarPorId(odontologoId);
        return (p != null && o != null);
    }
}
