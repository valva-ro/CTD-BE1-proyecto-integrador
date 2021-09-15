package com.valva.proyectointegrador.service.impl;

import com.valva.proyectointegrador.model.Domicilio;
import com.valva.proyectointegrador.repository.IDomicilioRepository;
import com.valva.proyectointegrador.service.IDomicilioService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DomicilioService implements IDomicilioService {

    @Autowired
    private IDomicilioRepository domicilioRepository;

    private final Logger logger = Logger.getLogger(DomicilioService.class);

    public List<Domicilio> buscar(String calle) {
        logger.debug("Iniciando método 'buscarPorId()' por calle");
        List<Domicilio> domicilios = null;
        try {
            domicilios = domicilioRepository.buscar(calle).orElse(new ArrayList<>());
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        logger.debug("Terminó la ejecución del método 'buscarPorId()' por calle");
        return domicilios;
    }

    public List<Domicilio> buscar(String calle, Integer numero) {
        logger.debug("Iniciando método 'buscarPorId()' por calle y numero");
        List<Domicilio> domicilios = null;
        try {
            domicilios = domicilioRepository.buscar(calle, numero).orElse(new ArrayList<>());
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        logger.debug("Terminó la ejecución del método 'buscarPorId()' por calle y numero");
        return domicilios;
    }

    public Domicilio buscar(String calle, Integer numero, String localidad, String provincia) {
        logger.debug("Iniciando método 'buscarPorId()' por calle, numero, localidad y provincia");
        Domicilio domicilio = null;
        try {
            domicilio = domicilioRepository.buscar(calle, numero, localidad, provincia).get();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        logger.debug("Terminó la ejecución del método 'buscarPorId()' por calle, numero, localidad y provincia");
        return domicilio;
    }

    @Override
    public Domicilio buscarPorId(Integer id) {
        logger.debug("Iniciando método 'buscarPorId()' por id");
        Domicilio domicilio = null;
        try {
            if (domicilioRepository.findById(id).isPresent())
                domicilio = domicilioRepository.findById(id).get();
            else
                throw new Exception("No se encontró el domicilio con id " + id);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        logger.debug("Terminó la ejecución del método 'buscarPorId()' por id");
        return domicilio;
    }

    @Override
    public Domicilio crear(Domicilio domicilio) {
        logger.debug("Iniciando método 'crear()'");
        Domicilio domicilioInsertado = null;
        try {
            domicilioInsertado = domicilioRepository.save(domicilio);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        logger.debug("Terminó la ejecución del método 'crear()'");
        return domicilioInsertado;
    }

    @Override
    public Domicilio actualizar(Domicilio domicilio) {
        logger.debug("Iniciando método 'actualizar()'");
        Domicilio domicilioActualizado = null;
        try {
            if (domicilioRepository.existsById(domicilio.getId()))
                domicilioActualizado = domicilioRepository.save(domicilio);
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
    public List<Domicilio> consultarTodos() {
        logger.debug("Iniciando método 'consultarTodos()'");
        List<Domicilio> domicilios = new ArrayList<>();
        try {
            domicilios = domicilioRepository.findAll();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        logger.debug("Terminó la ejecución del método 'consultarTodos()'");
        return domicilios;
    }
}
