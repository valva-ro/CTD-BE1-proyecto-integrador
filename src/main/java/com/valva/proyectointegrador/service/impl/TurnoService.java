package com.valva.proyectointegrador.service.impl;

import com.valva.proyectointegrador.config.SpringConfig;
import com.valva.proyectointegrador.model.OdontologoDto;
import com.valva.proyectointegrador.model.PacienteDto;
import com.valva.proyectointegrador.model.TurnoDto;
import com.valva.proyectointegrador.persistence.model.Turno;
import com.valva.proyectointegrador.persistence.repository.ITurnoRepository;
import com.valva.proyectointegrador.service.CRUDService;
import com.valva.proyectointegrador.service.ITurnoService;
import org.apache.log4j.Logger;
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
    private final Logger logger = Logger.getLogger(TurnoService.class);

    @Override
    public List<TurnoDto> buscar(String nombrePaciente, String apellidoPaciente, String nombreOdontologo, String apellidoOdontologo) {
        logger.debug("Iniciando método 'buscar()' por nombres y apellidos");
        List<TurnoDto> turnosDto = new ArrayList<>();
        try {
            List<Turno> turnos = turnoRepository.buscar(nombrePaciente, apellidoPaciente, nombreOdontologo, apellidoOdontologo).orElse(new ArrayList<>());
            turnosDto = springConfig.getModelMapper().map(turnos, turnosDto.getClass());
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        logger.debug("Terminó la ejecución del método 'buscar()' por nombres y apellidos");
        return turnosDto;
    }

    @Override
    public List<TurnoDto> buscar(String nombreOdontologo, String apellidoOdontologo) {
        logger.debug("Iniciando método 'buscar()' por nombre y apellido del odontologo");
        List<TurnoDto> turnosDto = new ArrayList<>();
        try {
            List<Turno> turnos = turnoRepository.buscar(nombreOdontologo, apellidoOdontologo).orElse(new ArrayList<>());
            turnosDto = springConfig.getModelMapper().map(turnos, turnosDto.getClass());
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        logger.debug("Terminó la ejecución del método 'buscar()' por nombre y apellido del odontologo");
        return turnosDto;
    }

    @Override
    public List<TurnoDto> buscar(Integer matricula, Integer dni) {
        logger.debug("Iniciando método 'buscar()' por matricula de odontologo y dni de paciente");
        List<TurnoDto> turnosDto = new ArrayList<>();
        try {
            List<Turno> turnos = turnoRepository.buscar(matricula, dni).orElse(new ArrayList<>());
            turnosDto = springConfig.getModelMapper().map(turnos, turnosDto.getClass());
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        logger.debug("Terminó la ejecución del método 'buscar()' por matricula de odontologo y dni de paciente");
        return turnosDto;
    }

    @Override
    public TurnoDto buscarPorId(Integer id) {
        logger.debug("Iniciando método 'buscarPorId()'");
        TurnoDto turnoDto = null;
        Turno turno = turnoRepository.findById(id).orElse(null);
        try {
            if (turno == null)
                throw new Exception("No se encontró el turno con id " + id);
            turnoDto = springConfig.getModelMapper().map(turno, TurnoDto.class);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        logger.debug("Terminó la ejecución del método 'buscarPorId()'");
        return turnoDto;
    }

    @Override
    public TurnoDto crear(TurnoDto turnoDto) {
        logger.debug("Iniciando método 'crear()'");
        try {
            Integer pacienteId = turnoDto.getPaciente().getId();
            Integer odontologoId = turnoDto.getOdontologo().getId();
            if (this.existenPacienteYOdontologo(pacienteId, odontologoId)) {
                Turno turno = springConfig.getModelMapper().map(turnoDto, Turno.class);
                turnoDto = springConfig.getModelMapper().map(turnoRepository.save(turno), TurnoDto.class);
                turnoDto.setPaciente(pacienteService.buscarPorId(pacienteId));
                turnoDto.setOdontologo(odontologoService.buscarPorId(odontologoId));
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        logger.debug("Terminó la ejecución del método 'crear()'");
        return turnoDto;
    }

    @Override
    public TurnoDto actualizar(TurnoDto turnoDto) {
        logger.debug("Iniciando método 'actualizar()'");
        TurnoDto turnoActualizado = null;
        try {
            if (turnoDto == null || turnoDto.getId() == null)
                throw new Exception("No se pudo actualizar el turno " + turnoDto);
            Optional<Turno> turnoEnBD = turnoRepository.findById(turnoDto.getId());
            if (turnoEnBD.isPresent()) {
                Turno actualizado = this.actualizar(turnoEnBD.get(), turnoDto);
                Turno guardado = turnoRepository.save(actualizado);
                turnoActualizado = springConfig.getModelMapper().map(guardado, TurnoDto.class);
            }
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
            turnoRepository.deleteById(id);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        logger.debug("Terminó la ejecución del método 'eliminar()'");
    }

    @Override
    public List<TurnoDto> consultarTodos() {
        logger.debug("Iniciando método 'consultarTodos()'");
        List<TurnoDto> turnosDto = new ArrayList<>();
        try {
            List<Turno> turnos = turnoRepository.findAll();
            turnosDto = springConfig.getModelMapper().map(turnos, turnosDto.getClass());
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        logger.debug("Terminó la ejecución del método 'consultarTodos()'");
        return turnosDto;
    }

    private Turno actualizar(Turno turno, TurnoDto turnoDto) {
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

    private boolean existenPacienteYOdontologo(Integer pacienteId, Integer odontologoId) {
        PacienteDto p = pacienteService.buscarPorId(pacienteId);
        OdontologoDto o = odontologoService.buscarPorId(odontologoId);
        return (p != null && o != null);
    }
}
