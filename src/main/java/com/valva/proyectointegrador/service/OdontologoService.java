package com.valva.proyectointegrador.service;

import com.valva.proyectointegrador.repository.configuration.ConfiguracionJDBC;
import com.valva.proyectointegrador.repository.IRepository;
import com.valva.proyectointegrador.repository.impl.OdontologoRepositoryH2;
import com.valva.proyectointegrador.model.Odontologo;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class OdontologoService implements CRUDService<Odontologo> {

    private IRepository<Odontologo> odontologoIRepository;
    private Logger logger = Logger.getLogger(OdontologoService.class);

    public OdontologoService() {
        this.odontologoIRepository = new OdontologoRepositoryH2(new ConfiguracionJDBC());
    }

    public OdontologoService(ConfiguracionJDBC configuracionJDBC) {
        this.odontologoIRepository = new OdontologoRepositoryH2(configuracionJDBC);
    }

    public Odontologo buscar(Integer id) {
        logger.debug("Iniciando método 'buscar()'");
        Odontologo odontologo = null;
        try {
            odontologo = odontologoIRepository.consultarPorId(id);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        logger.debug("Terminó la ejecución del método 'buscar()'");
        return odontologo;
    }

    public Odontologo crear(Odontologo odontologo) {
        logger.debug("Iniciando método 'crear()'");
        Odontologo odontologoInsertado = null;
        try {
            odontologoInsertado = odontologoIRepository.insertarNuevo(odontologo);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        logger.debug("Terminó la ejecución del método 'crear()'");
        return odontologoInsertado;
    }

    public Odontologo actualizar(Odontologo odontologo) {
        logger.debug("Iniciando método 'actualizar()'");
        Odontologo odontologoActualizado = null;
        try {
            odontologoActualizado = odontologoIRepository.actualizar(odontologo);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        logger.debug("Terminó la ejecución del método 'actualizar()'");
        return odontologoActualizado;
    }

    public void eliminar(Integer id) {
        logger.debug("Iniciando método 'eliminar()'");
        try {
            odontologoIRepository.eliminar(id);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        logger.debug("Terminó la ejecución del método 'eliminar()'");
    }

    public List<Odontologo> consultarTodos() {
        logger.debug("Iniciando método 'consultarTodos()'");
        List<Odontologo> odontologos = new ArrayList<>();
        try {
            odontologos = odontologoIRepository.consultarTodos();
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        logger.debug("Terminó la ejecución del método 'consultarTodos()'");
        return odontologos;
    }
}
