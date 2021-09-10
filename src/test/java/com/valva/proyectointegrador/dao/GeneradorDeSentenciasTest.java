package com.valva.proyectointegrador.dao;

import org.apache.log4j.PropertyConfigurator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class GeneradorDeSentenciasTest {

    @BeforeAll
    public static void init() {
        PropertyConfigurator.configure("src/test/resources/log4j.properties");
    }

    @Test
    public void test01GenerarInsert() throws Exception {
        String queryGenerada = GeneradorDeSentencias.generarInsert("personas", List.of("nombre", "apellido", "direccion", "mail", "edad"));
        String queryEsperada = "INSERT INTO personas (nombre,apellido,direccion,mail,edad) VALUES (?,?,?,?,?);";
        assertEquals(queryEsperada, queryGenerada);
    }

    @Test
    public void test02NoSePuedeGenerarInsertSiLaListaEstaVacia() {
        assertThrows(Exception.class, () -> GeneradorDeSentencias.generarInsert("personas", List.of()));
    }

    @Test
    public void test03GenerarUpdate() throws Exception {
        String queryGenerada = GeneradorDeSentencias.generarUpdate("personas", List.of("nombre", "apellido", "direccion", "mail", "edad"));
        String queryEsperada = "UPDATE personas SET nombre=?,apellido=?,direccion=?,mail=?,edad=? WHERE id=?;";
        assertEquals(queryEsperada, queryGenerada);
    }

    @Test
    public void test04NoSePuedeGenerarUpdateSiLaListaEstaVacia() {
        assertThrows(Exception.class, () -> GeneradorDeSentencias.generarUpdate("personas", List.of()));
    }
}
