package com.valva.proyectointegrador.service.impl;

import com.valva.proyectointegrador.model.Domicilio;
import com.valva.proyectointegrador.repository.IDomicilioRepository;
import com.valva.proyectointegrador.service.CRUDService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DomicilioService implements CRUDService<Domicilio> {

    @Autowired
    private IDomicilioRepository domicilioRepository;

    private final Logger logger = Logger.getLogger(DomicilioService.class);

    @Override
    public Domicilio buscar(Integer id) {
        logger.debug("Iniciando método 'buscar()'");
        Domicilio domicilio = null;
        try {
            if (domicilioRepository.findById(id).isPresent())
                domicilio = domicilioRepository.findById(id).get();
            else
                throw new Exception("No se encontró el domicilio con id " + id);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        logger.debug("Terminó la ejecución del método 'buscar()'");
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
