package com.valva.proyectointegrador.dao.impl;

import com.valva.proyectointegrador.dao.configuration.ConfiguracionJDBC;
import com.valva.proyectointegrador.dao.GeneradorDeSentencias;
import com.valva.proyectointegrador.dao.IDao;
import com.valva.proyectointegrador.model.Domicilio;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository("domicilioDao")
public class DomicilioDaoH2 implements IDao<Domicilio> {

    private final ConfiguracionJDBC configuracionJDBC;
    private final List<String> campos = List.of("calle", "numero", "localidad", "provincia");

    public DomicilioDaoH2() {
        this.configuracionJDBC = new ConfiguracionJDBC();
    }

    public DomicilioDaoH2(ConfiguracionJDBC configuracionJDBC) throws Exception {
        if (configuracionJDBC == null) {
            throw new Exception("¡Sin configuración de JDBC no hay DAO!");
        }
        this.configuracionJDBC = configuracionJDBC;
    }

    @Override
    public Domicilio consultarPorId(Integer id) throws SQLException {
        Connection connection = configuracionJDBC.obtenerConexionConBD();
        PreparedStatement preparedStatement = connection.prepareStatement(GeneradorDeSentencias.generarSelectPorId("domicilios"));
        preparedStatement.setInt(1, id);
        ResultSet result = preparedStatement.executeQuery();
        Domicilio domicilio = new Domicilio();

        if (result.next()) {
            domicilio.setId(result.getInt("id"));
            domicilio.setCalle(result.getString("calle"));
            domicilio.setNumero(result.getInt("numero"));
            domicilio.setLocalidad(result.getString("localidad"));
            domicilio.setProvincia(result.getString("provincia"));
        }

        connection.close();
        return domicilio;
    }

    @Override
    public List<Domicilio> consultarTodos() throws SQLException {
        Connection connection = configuracionJDBC.obtenerConexionConBD();
        PreparedStatement preparedStatement = connection.prepareStatement(GeneradorDeSentencias.generarSelectAll("domicilios"));
        ResultSet results = preparedStatement.executeQuery();
        List<Domicilio> domicilios = new ArrayList<>();

        while (results.next()) {
            Domicilio domicilio = new Domicilio(
                    results.getInt("id"),
                    results.getString("calle"),
                    results.getInt("numero"),
                    results.getString("localidad"),
                    results.getString("provincia")
            );
            domicilios.add(domicilio);
        }
        return domicilios;
    }

    @Override
    public Domicilio insertarNuevo(Domicilio domicilio) throws Exception {
        Connection connection = configuracionJDBC.obtenerConexionConBD();
        PreparedStatement preparedStatement = connection.prepareStatement(
                GeneradorDeSentencias.generarInsert("domicilios", campos),
                Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, domicilio.getCalle());
        preparedStatement.setInt(2, domicilio.getNumero());
        preparedStatement.setString(3, domicilio.getLocalidad());
        preparedStatement.setString(4, domicilio.getProvincia());
        preparedStatement.execute();
        ResultSet keys = preparedStatement.getGeneratedKeys();
        if (keys.next()) {
            domicilio.setId(keys.getInt("id"));
        }
        connection.close();
        return domicilio;
    }

    @Override
    public Domicilio actualizar(Domicilio domicilio) throws Exception {
        if (domicilio == null) throw new Exception("El domicilio no puede ser null");
        Connection connection = configuracionJDBC.obtenerConexionConBD();
        PreparedStatement preparedStatement = connection.prepareStatement(GeneradorDeSentencias.generarUpdate("domicilios", campos));
        preparedStatement.setString(1, domicilio.getCalle());
        preparedStatement.setInt(2, domicilio.getNumero());
        preparedStatement.setString(3, domicilio.getLocalidad());
        preparedStatement.setString(4, domicilio.getProvincia());
        preparedStatement.setInt(5, domicilio.getId());
        preparedStatement.execute();
        connection.close();
        return domicilio;
    }

    @Override
    public void eliminar(Integer id) throws SQLException {
        Connection connection = configuracionJDBC.obtenerConexionConBD();
        PreparedStatement preparedStatement = connection.prepareStatement(GeneradorDeSentencias.generarDeletePorId("domicilios"));
        preparedStatement.setInt(1, id);
        preparedStatement.execute();
        connection.close();
    }
}
