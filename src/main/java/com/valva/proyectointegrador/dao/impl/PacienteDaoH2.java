package com.valva.proyectointegrador.dao.impl;

import com.valva.proyectointegrador.dao.configuration.ConfiguracionJDBC;
import com.valva.proyectointegrador.dao.GeneradorDeSentencias;
import com.valva.proyectointegrador.dao.IDao;
import com.valva.proyectointegrador.model.Domicilio;
import com.valva.proyectointegrador.model.Paciente;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository("pacienteDao")
public class PacienteDaoH2 implements IDao<Paciente> {

    private final ConfiguracionJDBC configuracionJDBC;
    private final DomicilioDaoH2 domicilioRepositoryH2;
    private final List<String> campos = List.of("nombre", "apellido", "dni", "fechaIngreso", "id");

    public PacienteDaoH2() throws Exception {
        this.configuracionJDBC = new ConfiguracionJDBC();
        this.domicilioRepositoryH2 = new DomicilioDaoH2(configuracionJDBC);
    }

    public PacienteDaoH2(ConfiguracionJDBC configuracionJDBC) throws Exception {
        if (configuracionJDBC == null) {
            throw new Exception("¡Sin configuración de JDBC no hay DAO!");
        }
        this.configuracionJDBC = configuracionJDBC;
        this.domicilioRepositoryH2 = new DomicilioDaoH2(configuracionJDBC);
    }

    @Override
    public Paciente consultarPorId(Integer id) throws Exception {
        if (id == null) throw new Exception("El id no puede ser null");
        Connection connection = configuracionJDBC.obtenerConexionConBD();
        PreparedStatement preparedStatement = connection.prepareStatement(GeneradorDeSentencias.generarSelectPorId("pacientes"));
        preparedStatement.setInt(1, id);
        ResultSet result = preparedStatement.executeQuery();
        Paciente paciente = new Paciente();

        if (result.next()) {
            paciente.setId(result.getInt("id"));
            paciente.setNombre(result.getString("nombre"));
            paciente.setApellido(result.getString("apellido"));
            paciente.setDni(result.getInt("dni"));
            paciente.setFechaIngreso(result.getDate("fechaIngreso").toLocalDate());
            paciente.setDomicilio(domicilioRepositoryH2.consultarPorId(result.getInt("id")));
        } else {
            throw new Exception("No existe ningun paciente con ese ID");
        }

        connection.close();
        return paciente;
    }

    @Override
    public List<Paciente> consultarTodos() throws SQLException {
        Connection connection = configuracionJDBC.obtenerConexionConBD();
        PreparedStatement preparedStatement = connection.prepareStatement(GeneradorDeSentencias.generarSelectAll("pacientes"));
        ResultSet results = preparedStatement.executeQuery();
        List<Paciente> pacientes = new ArrayList<>();

        while (results.next()) {
            Paciente paciente = new Paciente(
                    results.getInt("id"),
                    results.getString("nombre"),
                    results.getString("apellido"),
                    results.getInt("dni"),
                    results.getDate("fechaIngreso").toLocalDate(),
                    domicilioRepositoryH2.consultarPorId(results.getInt("id"))
            );
            pacientes.add(paciente);
        }
        return pacientes;
    }

    @Override
    public Paciente insertarNuevo(Paciente paciente) throws Exception {
        if (paciente == null) throw new Exception("El paciente no puede ser null");
        Connection connection = configuracionJDBC.obtenerConexionConBD();
        PreparedStatement preparedStatement = connection.prepareStatement(
                GeneradorDeSentencias.generarInsert("pacientes", campos),
                Statement.RETURN_GENERATED_KEYS);
        Domicilio domicilio;
        if (paciente.getDomicilio().getId() == null) {
            domicilio = domicilioRepositoryH2.insertarNuevo(paciente.getDomicilio());
        } else {
            domicilio = domicilioRepositoryH2.consultarPorId(paciente.getDomicilio().getId());
        }
        paciente.setDomicilio(domicilio);
        preparedStatement.setString(1, paciente.getNombre());
        preparedStatement.setString(2, paciente.getApellido());
        preparedStatement.setInt(3, paciente.getDni());
        preparedStatement.setDate(4, Date.valueOf(paciente.getFechaIngreso()));
        preparedStatement.setInt(5, paciente.getDomicilio().getId());
        preparedStatement.execute();
        ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
        if (generatedKeys.next()) {
            paciente.setId(generatedKeys.getInt("id"));
        }
        connection.close();
        return paciente;
    }

    @Override
    public Paciente actualizar(Paciente paciente) throws Exception {
        if (paciente == null) throw new Exception("El paciente no puede ser null");
        Connection connection = configuracionJDBC.obtenerConexionConBD();
        PreparedStatement preparedStatement = connection.prepareStatement(GeneradorDeSentencias.generarUpdate("pacientes", campos));
        preparedStatement.setString(1, paciente.getNombre());
        preparedStatement.setString(2, paciente.getApellido());
        preparedStatement.setInt(3, paciente.getDni());
        preparedStatement.setDate(4, Date.valueOf(paciente.getFechaIngreso()));
        preparedStatement.setInt(5, paciente.getDomicilio().getId());
        preparedStatement.setInt(6, paciente.getId());
        preparedStatement.execute();
        connection.close();
        return paciente;
    }

    @Override
    public void eliminar(Integer id) throws SQLException {
        Connection connection = configuracionJDBC.obtenerConexionConBD();
        PreparedStatement preparedStatement = connection.prepareStatement(GeneradorDeSentencias.generarDeletePorId("pacientes"));
        preparedStatement.setInt(1, id);
        preparedStatement.execute();
        connection.close();
    }
}
