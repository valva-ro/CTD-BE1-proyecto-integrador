package com.valva.proyectointegrador.service.impl;

import com.valva.proyectointegrador.dto.DomicilioDto;
import com.valva.proyectointegrador.persistence.model.Domicilio;
import com.valva.proyectointegrador.persistence.repository.IDomicilioRepository;
import com.valva.proyectointegrador.service.IDomicilioService;
import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DomicilioService implements IDomicilioService {

    @Autowired
    private IDomicilioRepository domicilioRepository;

    private final ModelMapper modelMapper = new ModelMapper();

    private final Logger logger = Logger.getLogger(DomicilioService.class);

    public List<DomicilioDto> buscar(String calle) {
        logger.debug("Iniciando método 'buscarPorId()' por calle");
        List<DomicilioDto> domiciliosDto = new ArrayList<>();
        try {
            List<Domicilio> domicilios = domicilioRepository.buscar(calle).orElse(new ArrayList<>());
            modelMapper.map(domicilios, domiciliosDto);
            domiciliosDto = modelMapper.map(domicilios, domiciliosDto.getClass());
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        logger.debug("Terminó la ejecución del método 'buscarPorId()' por calle");
        return domiciliosDto;
    }

    public List<DomicilioDto> buscar(String calle, Integer numero) {
        logger.debug("Iniciando método 'buscarPorId()' por calle y numero");
        List<DomicilioDto> domiciliosDto = new ArrayList<>();
        try {
            List<Domicilio> domicilios = domicilioRepository.buscar(calle, numero).orElse(new ArrayList<>());
            domiciliosDto = modelMapper.map(domicilios, domiciliosDto.getClass());
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        logger.debug("Terminó la ejecución del método 'buscarPorId()' por calle y numero");
        return domiciliosDto;
    }

    public DomicilioDto buscar(String calle, Integer numero, String localidad, String provincia) {
        logger.debug("Iniciando método 'buscarPorId()' por calle, numero, localidad y provincia");
        DomicilioDto domicilioDto = null;
        Domicilio domicilio = domicilioRepository.buscar(calle, numero, localidad, provincia).orElse(null);
        try {
            if (domicilio == null)
                throw new Exception("No se encontró el domicilio");
            domicilioDto = modelMapper.map(domicilio, DomicilioDto.class);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        logger.debug("Terminó la ejecución del método 'buscarPorId()' por calle, numero, localidad y provincia");
        return domicilioDto;
    }

    @Override
    public DomicilioDto buscarPorId(Integer id) {
        logger.debug("Iniciando método 'buscarPorId()' por id");
        DomicilioDto domicilioDto = null;
        Domicilio domicilio = domicilioRepository.findById(id).orElse(null);
        try {
            if (domicilio == null)
                throw new Exception("No se encontró el domicilio con id " + id);
            domicilioDto = modelMapper.map(domicilio, DomicilioDto.class);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        logger.debug("Terminó la ejecución del método 'buscarPorId()' por id");
        return domicilioDto;
    }

    @Override
    public DomicilioDto crear(DomicilioDto domicilioDto) {
        logger.debug("Iniciando método 'crear()'");
        try {
            if (domicilioDto == null)
                throw new Exception("No se pudo guardar el domicilio " + domicilioDto);
            Domicilio domicilio = modelMapper.map(domicilioDto, Domicilio.class);
            domicilioDto = modelMapper.map(domicilioRepository.save(domicilio), DomicilioDto.class);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        logger.debug("Terminó la ejecución del método 'crear()'");
        return domicilioDto;
    }

    @Override
    public DomicilioDto actualizar(DomicilioDto domicilioDto) {
        logger.debug("Iniciando método 'actualizar()'");
        DomicilioDto domicilioActualizado = null;
        try {
            if (domicilioDto == null || domicilioDto.getId() == null)
                throw new Exception("No se pudo guardar el domicilio " + domicilioDto);
            Optional<Domicilio> domicilioEnBD = domicilioRepository.findById(domicilioDto.getId());
            if (domicilioEnBD.isPresent()) {
                Domicilio guardado = domicilioRepository.save(domicilioEnBD.get());
                domicilioActualizado = modelMapper.map(guardado, DomicilioDto.class);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        logger.debug("Terminó la ejecución del método 'actualizar()'");
        return domicilioActualizado;
    }

    @Override
    public void eliminar(Integer id) {
        logger.debug("Iniciando método 'eliminar()'");
        try {
            domicilioRepository.deleteById(id);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        logger.debug("Terminó la ejecución del método 'eliminar()'");
    }

    @Override
    public List<DomicilioDto> consultarTodos() {
        logger.debug("Iniciando método 'consultarTodos()'");
        List<DomicilioDto> domiciliosDto = new ArrayList<>();
        try {
            domiciliosDto = modelMapper.map(domicilioRepository.findAll(), domiciliosDto.getClass());
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        logger.debug("Terminó la ejecución del método 'consultarTodos()'");
        return domiciliosDto;
    }
}
