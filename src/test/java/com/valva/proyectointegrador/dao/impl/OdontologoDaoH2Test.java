package com.valva.proyectointegrador.dao.impl;

import com.valva.proyectointegrador.model.Odontologo;
import org.apache.log4j.PropertyConfigurator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class OdontologoDaoH2Test {

    private OdontologoDaoH2 odontologoDaoH2;

    @BeforeEach
    public void reset() {
        odontologoDaoH2 = new OdontologoDaoH2();
    }

    @BeforeAll
    public static void init() {
        PropertyConfigurator.configure("src/test/resources/log4j.properties");
    }

    @Test
    public void test01Insertar() throws Exception {
        Odontologo odontologoOriginal = new Odontologo("Juan", "Perez", "121212");
        Odontologo odontologoInsertado = odontologoDaoH2.insertarNuevo(odontologoOriginal);
        assertNotNull(odontologoInsertado.getId());
    }

    @Test
    public void test02NoSePuedeInstanciarSinConfiguracion() {
        assertThrows(Exception.class, () -> new OdontologoDaoH2(null));
    }

    @Test
    public void test03NoSePuedeInsertarNull() {
        assertThrows(Exception.class, () -> odontologoDaoH2.insertarNuevo(null));
    }

    @Test
    public void test04ConsultarPorIDExistente() throws Exception {
        Odontologo odontologoExistente = new Odontologo(1, "Pepe", "Pepardo", "123456");
        Odontologo odontologoInsertado = odontologoDaoH2.consultarPorId(1);
        assertEquals(odontologoExistente, odontologoInsertado);
    }

    @Test
    public void test05ConsultarPorIDInexistenteLanzaError() {
        assertThrows(Exception.class, () ->  odontologoDaoH2.consultarPorId(-12));
    }

    @Test
    public void test06ConsultarTodos() throws Exception {
        odontologoDaoH2.insertarNuevo(new Odontologo("Maria", "Rodríguez", "987654"));
        List<Odontologo> odontologos = odontologoDaoH2.consultarTodos();
        assertNotEquals(0, odontologos.size());
    }

    @Test
    public void test07ActualizarSinIdLanzaError() {
        assertThrows(Exception.class, () -> odontologoDaoH2.actualizar(new Odontologo("Pepito", "Peposo", "123456")));
    }
    
    @Test
    public void test08ConsultarTodos() throws SQLException {
        // Hay 3 odontologos que se cargan en create.sql
        List<Odontologo> odontologos = odontologoDaoH2.consultarTodos();
        assertNotEquals(0, odontologos.size());
    }
}
