package com.valva.proyectointegrador.repository.impl;

import com.valva.proyectointegrador.repository.configuration.ConfiguracionJDBC;
import com.valva.proyectointegrador.model.Domicilio;
import com.valva.proyectointegrador.model.Paciente;
import org.apache.log4j.PropertyConfigurator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class PacienteDaoH2Test {

    private PacienteRepositoryH2 pacienteDaoH2 = new PacienteRepositoryH2(new ConfiguracionJDBC());

    public PacienteDaoH2Test() throws Exception {}

    @BeforeAll
    public static void init() {
        PropertyConfigurator.configure("src/test/resources/log4j.properties");
    }

    @Test
    public void test01NoSePuedeInstanciarSinConfiguracion() throws Exception {
        assertThrows(Exception.class,
                () -> new PacienteRepositoryH2(null));
    }

    @Test
    public void test02InsertarPaciente() throws Exception {
        Paciente pacienteOriginal = new Paciente("Pepa", "Peparda", 123456789, LocalDate.of(2021, 8, 18), new Domicilio(1));
        Paciente pacienteInsertado = pacienteDaoH2.insertarNuevo(pacienteOriginal);
        assertNotNull(pacienteInsertado.getId());
    }

    @Test
    public void test03NoSePuedeInsertarPacienteNull() throws Exception {
        assertThrows(Exception.class,
                () -> pacienteDaoH2.insertarNuevo(null));
    }

    @Test
    public void test04ConsultarPorID() throws Exception {
        Paciente pacienteExistente = pacienteDaoH2.insertarNuevo(new Paciente("Pepa", "Peparda", 123456789, LocalDate.of(2021, 8, 18), new Domicilio(1)));
        Paciente pacienteConsultado = pacienteDaoH2.consultarPorId(1);
        assertEquals(pacienteExistente, pacienteConsultado);
    }
}
