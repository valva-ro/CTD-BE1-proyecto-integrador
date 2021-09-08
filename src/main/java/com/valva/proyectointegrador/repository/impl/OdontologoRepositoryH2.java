package com.valva.proyectointegrador.repository.impl;

import com.valva.proyectointegrador.repository.configuration.ConfiguracionJDBC;
import com.valva.proyectointegrador.repository.GeneradorDeSentencias;
import com.valva.proyectointegrador.repository.IRepository;
import com.valva.proyectointegrador.model.Odontologo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OdontologoRepositoryH2 implements IRepository<Odontologo> {

    private ConfiguracionJDBC configuracionJDBC;
    private List<String> campos = List.of("nombre", "apellido", "numeroMatricula");

    public OdontologoRepositoryH2() {
        configuracionJDBC = new ConfiguracionJDBC();
    }

    public OdontologoRepositoryH2(ConfiguracionJDBC configuracionJDBC) {
        if (configuracionJDBC != null) {
            this.configuracionJDBC = configuracionJDBC;
        }
    }

    @Override
    public Odontologo consultarPorId(Integer id) throws SQLException, Exception {
        if (id == null) throw new Exception("El id no puede ser null");

        Connection connection = configuracionJDBC.obtenerConexionConBD();
        PreparedStatement preparedStatement = connection.prepareStatement(GeneradorDeSentencias.generarSelectPorId("odontologos"));
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        Odontologo odontologo = new Odontologo();

        if (resultSet.next()) {
            odontologo.setId(resultSet.getInt("id"));
            odontologo.setNombre(resultSet.getString("nombre"));
            odontologo.setApellido(resultSet.getString("apellido"));
            odontologo.setMatricula(resultSet.getString("numeroMatricula"));
        } else {
            throw new Exception("No existe ningun odontologo con ese ID");
        }

        resultSet.close();
        preparedStatement.close();
        connection.close();

        return odontologo;
    }

    @Override
    public List<Odontologo> consultarTodos() throws SQLException {

        Connection connection = configuracionJDBC.obtenerConexionConBD();
        PreparedStatement preparedStatement = connection.prepareStatement(GeneradorDeSentencias.generarSelectAll("odontologos"));
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Odontologo> odontologos = new ArrayList<>();

        while (resultSet.next()) {
            Odontologo paciente = new Odontologo(
                    resultSet.getInt("id"),
                    resultSet.getString("nombre"),
                    resultSet.getString("apellido"),
                    resultSet.getString("numeroMatricula")
            );
            odontologos.add(paciente);
        }

        resultSet.close();
        preparedStatement.close();
        connection.close();

        return odontologos;
    }

    @Override
    public Odontologo insertarNuevo(Odontologo odontologo) throws Exception {

        if (odontologo == null) throw new Exception("El odontologo no puede ser null");

        Connection connection = configuracionJDBC.obtenerConexionConBD();
        PreparedStatement preparedStatement = connection.prepareStatement(
                GeneradorDeSentencias.generarInsert("odontologos", campos),
                Statement.RETURN_GENERATED_KEYS);

        preparedStatement.setString(1, odontologo.getNombre());
        preparedStatement.setString(2, odontologo.getApellido());
        preparedStatement.setString(3, odontologo.getMatricula());
        preparedStatement.execute();

        ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
        if (generatedKeys.next()) {
            odontologo.setId(generatedKeys.getInt("id"));
        }

        preparedStatement.close();
        connection.close();

        return odontologo;
    }

    @Override
    public Odontologo actualizar(Odontologo odontologo) throws Exception {

        if (odontologo == null) throw new Exception("El odontologo no puede ser null");
        if (odontologo.getId() == null) throw new Exception("El odontologo debe tener un id");

        Connection connection = configuracionJDBC.obtenerConexionConBD();
        PreparedStatement preparedStatement = connection.prepareStatement(GeneradorDeSentencias.generarUpdate("odontologos", campos));

        preparedStatement.setString(1, odontologo.getNombre());
        preparedStatement.setString(2, odontologo.getApellido());
        preparedStatement.setString(3, odontologo.getMatricula());
        preparedStatement.setInt(4, odontologo.getId());
        preparedStatement.execute();

        preparedStatement.close();
        connection.close();

        return odontologo;
    }

    @Override
    public void eliminar(Integer id) throws SQLException {
        Connection connection = configuracionJDBC.obtenerConexionConBD();
        PreparedStatement preparedStatement = connection.prepareStatement(GeneradorDeSentencias.generarDeletePorId("odontologos"));
        preparedStatement.setInt(1, id);
        preparedStatement.execute();
        connection.close();
    }
}
