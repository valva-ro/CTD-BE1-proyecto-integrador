package com.valva.proyectointegrador.service.impl;

import com.valva.proyectointegrador.model.Odontologo;
import com.valva.proyectointegrador.repository.IOdontologoRepository;
import com.valva.proyectointegrador.service.IOdontologoService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OdontologoService implements IOdontologoService {

    @Autowired
    private IOdontologoRepository odontologoRepository;

    private final Logger logger = Logger.getLogger(OdontologoService.class);

    @Override
    public Odontologo buscar(Integer matricula) {
        logger.debug("Iniciando método 'buscar()' por matricula");
        Odontologo odontologo = null;
        try {
            odontologo = odontologoRepository.buscar(matricula).get();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        logger.debug("Terminó la ejecución del método 'buscar()' por matricula");
        return odontologo;
    }

    @Override
    public List<Odontologo> buscar(String nombre) {
        logger.debug("Iniciando método 'buscar()' por nombre");
        List<Odontologo> odontologos = null;
        try {
            odontologos = odontologoRepository.buscar(nombre).orElse(new ArrayList<>());
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        logger.debug("Terminó la ejecución del método 'buscar()' por nombre");
        return odontologos;
    }

    @Override
    public List<Odontologo> buscar(String nombre, String apellido) {
        logger.debug("Iniciando método 'buscar()' por nombre y apellido");
        List<Odontologo> odontologos = null;
        try {
            odontologos = odontologoRepository.buscar(nombre, apellido).orElse(new ArrayList<>());
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        logger.debug("Terminó la ejecución del método 'buscar()' por nombre y apellido");
        return odontologos;
    }

    @Override
    public Odontologo buscarPorId(Integer id) {
        logger.debug("Iniciando método 'buscarPorId()'");
        Odontologo odontologo = null;
        try {
            if (odontologoRepository.findById(id).isPresent())
                odontologo = odontologoRepository.findById(id).get();
            else
                throw new Exception("No se encontró el odontólogo con id " + id);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        logger.debug("Terminó la ejecución del método 'buscarPorId()'");
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
