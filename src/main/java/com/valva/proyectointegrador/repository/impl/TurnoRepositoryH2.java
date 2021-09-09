package com.valva.proyectointegrador.repository.impl;

import com.valva.proyectointegrador.model.Turno;
import com.valva.proyectointegrador.repository.GeneradorDeSentencias;
import com.valva.proyectointegrador.repository.IRepository;
import com.valva.proyectointegrador.repository.configuration.ConfiguracionJDBC;
import com.valva.proyectointegrador.util.Util;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TurnoRepositoryH2 implements IRepository<Turno> {

    private ConfiguracionJDBC configuracionJDBC;
    private List<String> campos = List.of("idPaciente", "idOdontologo", "fecha");

    public TurnoRepositoryH2() {
        this.configuracionJDBC = new ConfiguracionJDBC();
    }

    public TurnoRepositoryH2(ConfiguracionJDBC configuracionJDBC) throws Exception {
        if (configuracionJDBC == null) {
            throw new Exception("¡Sin configuración de JDBC no hay DAO!");
        }
        this.configuracionJDBC = configuracionJDBC;
    }

    @Override
    public Turno insertarNuevo(Turno turno) throws Exception {
        Connection connection = configuracionJDBC.obtenerConexionConBD();
        PreparedStatement preparedStatement = connection.prepareStatement(
                GeneradorDeSentencias.generarInsert("turnos", campos),
                Statement.RETURN_GENERATED_KEYS);

        preparedStatement.setInt(1, turno.getIdPaciente());
        preparedStatement.setInt(2, turno.getIdOdontologo());
        preparedStatement.setDate(3, Util.convertLocalDateToSqlDate(turno.getFecha()));

        preparedStatement.executeUpdate();

        ResultSet idGenerado = preparedStatement.getGeneratedKeys();
        if (idGenerado.next()){
            turno.setId(idGenerado.getInt(1));
        }

        return turno;
    }

    @Override
    public Turno consultarPorId(Integer id) throws SQLException {
        Turno turno = null;
        Connection connection = configuracionJDBC.obtenerConexionConBD();
        PreparedStatement preparedStatement = connection.prepareStatement(GeneradorDeSentencias.generarSelectPorId("turnos"));
        preparedStatement.setInt(1, id);
        ResultSet result = preparedStatement.executeQuery();
        if (result.next()) {
            turno = crearObjetoTurno(result);
        }
        return turno;
    }

    @Override
    public void eliminar(Integer id) throws SQLException {
        Connection connection = configuracionJDBC.obtenerConexionConBD();
        PreparedStatement preparedStatement = connection.prepareStatement(GeneradorDeSentencias.generarDeletePorId("turnos"));
        preparedStatement.setInt(1, id);
        preparedStatement.execute();
        connection.close();
    }

    @Override
    public List<Turno> consultarTodos() throws SQLException {
        List<Turno> turnos = new ArrayList<>();

        Connection connection = configuracionJDBC.obtenerConexionConBD();
        PreparedStatement preparedStatement = connection.prepareStatement(GeneradorDeSentencias.generarSelectAll("turnos"));
        ResultSet result = preparedStatement.executeQuery();

        while (result.next()) {
            turnos.add(crearObjetoTurno(result));
        }
        return turnos;
    }

    @Override
    public Turno actualizar(Turno turno) throws Exception {
        if (turno == null) throw new Exception("El domicilio no puede ser null");
        Connection connection = configuracionJDBC.obtenerConexionConBD();
        PreparedStatement preparedStatement = connection.prepareStatement(GeneradorDeSentencias.generarUpdate("turnos", campos));
        preparedStatement.setInt(1, turno.getIdPaciente());
        preparedStatement.setInt(2, turno.getIdOdontologo());
        preparedStatement.setDate(3, Util.convertLocalDateToSqlDate(turno.getFecha()));
        preparedStatement.execute();
        connection.close();
        return turno;
    }

    private Turno crearObjetoTurno(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        int paciente = rs.getInt(campos.get(0));
        int odontologo = rs.getInt(campos.get(1));
        LocalDate fecha = Util.convertirSqlDateALocalDate(rs.getDate(campos.get(2)));
        return new Turno(id, paciente, odontologo, fecha);
    }
}
