package com.valva.proyectointegrador.service;

import com.valva.proyectointegrador.dao.configuration.ConfiguracionJDBC;
import com.valva.proyectointegrador.dao.IDao;
import com.valva.proyectointegrador.dao.impl.OdontologoDaoH2;
import com.valva.proyectointegrador.model.Odontologo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class OdontologoService implements CRUDService<Odontologo> {

    @Autowired
    @Qualifier("odontologoDao")
    private IDao<Odontologo> odontologoIDao;

    private final Logger logger = Logger.getLogger(OdontologoService.class);

    @Override
    public Odontologo buscar(Integer id) {
        logger.debug("Iniciando método 'buscar()'");
        Odontologo odontologo = null;
        try {
            odontologo = odontologoIDao.consultarPorId(id);
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
            odontologoInsertado = odontologoIDao.insertarNuevo(odontologo);
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
            odontologoActualizado = odontologoIDao.actualizar(odontologo);
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
            odontologoIDao.eliminar(id);
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
            odontologos = odontologoIDao.consultarTodos();
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        logger.debug("Terminó la ejecución del método 'consultarTodos()'");
        return odontologos;
    }
}
