package com.valva.proyectointegrador.service.impl;

import com.valva.proyectointegrador.model.Odontologo;
import com.valva.proyectointegrador.repository.IOdontologoRepository;
import com.valva.proyectointegrador.service.CRUDService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OdontologoService implements CRUDService<Odontologo> {

    @Autowired
    private IOdontologoRepository odontologoRepository;

    private final Logger logger = Logger.getLogger(OdontologoService.class);

    @Override
    public Odontologo buscar(Integer id) {
        logger.debug("Iniciando método 'buscar()'");
        Odontologo odontologo = null;
        try {
            if (odontologoRepository.findById(id).isPresent())
                odontologo = odontologoRepository.findById(id).get();
            else
                throw new Exception("No se encontró el odontólogo con id " + id);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        logger.debug("Terminó la ejecución del método 'buscar()'");
        return odontologo;
    }

    @Override
    public Odontologo crear(Odontologo odontologo) {
        logger.debug("Iniciando método 'crear()'");
        Odontologo odontologoInsertado = null;
        try {
            odontologoInsertado = odontologoRepository.save(odontologo);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        logger.debug("Terminó la ejecución del método 'crear()'");
        return odontologoInsertado;
    }

    @Override
    public Odontologo actualizar(Odontologo odontologo) {
        logger.debug("Iniciando método 'actualizar()'");
        Odontologo odontologoActualizado = null;
        try {
            if (odontologoRepository.existsById(odontologo.getId()))
                odontologoActualizado = odontologoRepository.save(odontologo);
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
    public List<Odontologo> consultarTodos() {
        logger.debug("Iniciando método 'consultarTodos()'");
        List<Odontologo> odontologos = new ArrayList<>();
        try {
            odontologos = odontologoRepository.findAll();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        logger.debug("Terminó la ejecución del método 'consultarTodos()'");
        return odontologos;
    }
}
