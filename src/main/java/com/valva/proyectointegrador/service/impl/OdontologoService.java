package com.valva.proyectointegrador.service.impl;

import com.valva.proyectointegrador.config.SpringConfig;
import com.valva.proyectointegrador.model.OdontologoDto;
import com.valva.proyectointegrador.persistence.model.Odontologo;
import com.valva.proyectointegrador.persistence.repository.IOdontologoRepository;
import com.valva.proyectointegrador.service.IOdontologoService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OdontologoService implements IOdontologoService {

    @Autowired
    private IOdontologoRepository odontologoRepository;
    @Autowired
    private SpringConfig springConfig;
    private final Logger logger = Logger.getLogger(OdontologoService.class);

    @Override
    public OdontologoDto buscar(Integer matricula) {
        logger.debug("Iniciando método 'buscar()' por matricula");
        OdontologoDto odontologoDto = null;
        Odontologo odontologo = odontologoRepository.buscar(matricula).orElse(null);
        try {
            if (odontologo == null)
                throw new Exception("No se encontró el odontólogo con matricula " + matricula);
            odontologoDto = springConfig.getModelMapper().map(odontologo, OdontologoDto.class);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        logger.debug("Terminó la ejecución del método 'buscar()' por matricula");
        return odontologoDto;
    }

    @Override
    public List<OdontologoDto> buscar(String nombre) {
        logger.debug("Iniciando método 'buscar()' por nombre");
        List<OdontologoDto> odontologosDto = new ArrayList<>();
        try {
            List<Odontologo> odontologos = odontologoRepository.buscar(nombre).orElse(new ArrayList<>());
            odontologosDto = springConfig.getModelMapper().map(odontologos, odontologosDto.getClass());
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        logger.debug("Terminó la ejecución del método 'buscar()' por nombre");
        return odontologosDto;
    }

    @Override
    public List<OdontologoDto> buscar(String nombre, String apellido) {
        logger.debug("Iniciando método 'buscar()' por nombre y apellido");
        List<OdontologoDto> odontologosDto = new ArrayList<>();
        try {
            List<Odontologo> odontologos = odontologoRepository.buscar(nombre, apellido).orElse(new ArrayList<>());
            odontologosDto = springConfig.getModelMapper().map(odontologos, odontologosDto.getClass());
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        logger.debug("Terminó la ejecución del método 'buscar()' por nombre y apellido");
        return odontologosDto;
    }

    @Override
    public OdontologoDto buscarPorId(Integer id) {
        logger.debug("Iniciando método 'buscarPorId()'");
        OdontologoDto odontologoDto = null;
        Odontologo odontologo = odontologoRepository.findById(id).orElse(null);
        try {
            if (odontologo == null)
                throw new Exception("No se encontró el odontólogo con id " + id);
            odontologoDto = springConfig.getModelMapper().map(odontologo, OdontologoDto.class);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        logger.debug("Terminó la ejecución del método 'buscarPorId()'");
        return odontologoDto;
    }

    @Override
    public OdontologoDto crear(OdontologoDto odontologoDto) {
        logger.debug("Iniciando método 'crear()'");
        try {
            if (odontologoDto == null)
                throw new Exception("No se pudo guardar el odontologo " + odontologoDto);
            Odontologo odontologo = springConfig.getModelMapper().map(odontologoDto, Odontologo.class);
            odontologoDto = springConfig.getModelMapper().map(odontologoRepository.save(odontologo), OdontologoDto.class);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        logger.debug("Terminó la ejecución del método 'crear()'");
        return odontologoDto;
    }

    @Override
    public OdontologoDto actualizar(OdontologoDto odontologoDto) {
        logger.debug("Iniciando método 'actualizar()'");
        OdontologoDto odontologoActualizado = null;
        try {
            if (odontologoDto == null || odontologoDto.getId() == null)
                throw new Exception("No se pudo actualizar el odontologo " + odontologoDto);
            Optional<Odontologo> odontologoEnBD = odontologoRepository.findById(odontologoDto.getId());
            if (odontologoEnBD.isPresent()) {
                Odontologo actualizado = this.actualizar(odontologoEnBD.get(), odontologoDto);
                Odontologo guardado = odontologoRepository.save(actualizado);
                odontologoActualizado = springConfig.getModelMapper().map(guardado, OdontologoDto.class);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        logger.debug("Terminó la ejecución del método 'actualizar()'");
        return odontologoActualizado;
    }

    @Override
    public void eliminar(Integer id) {
        logger.debug("Iniciando método 'eliminar()'");
        try {
            odontologoRepository.deleteById(id);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        logger.debug("Terminó la ejecución del método 'eliminar()'");
    }

    @Override
    public List<OdontologoDto> consultarTodos() {
        logger.debug("Iniciando método 'consultarTodos()'");
        List<OdontologoDto> odontologosDto = new ArrayList<>();
        try {
            List<Odontologo> odontologos = odontologoRepository.findAll();
            odontologosDto = springConfig.getModelMapper().map(odontologos, odontologosDto.getClass());
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        logger.debug("Terminó la ejecución del método 'consultarTodos()'");
        return odontologosDto;
    }

    private Odontologo actualizar(Odontologo odontologo, OdontologoDto odontologoDto) {
        if (odontologoDto.getNombre() != null) {
            odontologo.setNombre(odontologoDto.getNombre());
        }
        if (odontologoDto.getApellido() != null) {
            odontologo.setApellido(odontologoDto.getApellido());
        }
        if (odontologoDto.getMatricula() != null) {
            odontologo.setMatricula(odontologoDto.getMatricula());
        }
        return odontologo;
    }
}
