package com.valva.proyectointegrador.dao.impl;

import com.valva.proyectointegrador.dao.configuration.ConfiguracionJDBC;
import com.valva.proyectointegrador.model.Domicilio;
import org.apache.log4j.PropertyConfigurator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DomicilioDaoH2Test {

    private DomicilioDaoH2 domicilioDaoH2 = new DomicilioDaoH2(new ConfiguracionJDBC());

    public DomicilioDaoH2Test() throws Exception {}

    @BeforeAll
    public static void init() {
        PropertyConfigurator.configure("src/test/resources/log4j.properties");
    }

    @Test
    public void test01InsertarDomicilio() throws Exception {
        Domicilio domicilioOriginal = new Domicilio("Calle Verdadera", 321, "Springfield", "Springfield");
        Domicilio domicilioInsertado = domicilioDaoH2.insertarNuevo(domicilioOriginal);
        assertNotNull(domicilioInsertado.getId());
    }

    @Test
    public void test02NoSePuedeInstanciarSinConfiguracion() {
        assertThrows(Exception.class,
                () -> new DomicilioDaoH2(null));
    }

    @Test
    public void test03ConsultarPorID() throws Exception {
        Domicilio domicilioExistente = new Domicilio(1, "Calle Falsa", 123, "Springfield", "Springfield");
        Domicilio domicilioConsultado = domicilioDaoH2.consultarPorId(1);
        assertEquals(domicilioExistente, domicilioConsultado);
    }
}
