package com.valva.proyectointegrador.service.impl;

import com.valva.proyectointegrador.exceptions.service.PacienteServiceException;
import com.valva.proyectointegrador.model.DomicilioDto;
import com.valva.proyectointegrador.model.PacienteDto;
import com.valva.proyectointegrador.persistence.repository.IPacienteRepository;
import com.valva.proyectointegrador.service.IPacienteService;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runners.MethodSorters;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SpringBootTest
public class PacienteServiceTest {

    @Mock
    @Autowired
    private IPacienteRepository pacienteRepository;

    @Autowired
    @InjectMocks
    private IPacienteService pacienteService = new PacienteService();
    private PacienteDto paciente;

    @BeforeEach
    public void setUp() {
        DomicilioDto domicilio = new DomicilioDto();
        domicilio.setCalle("Calle Falsa");
        domicilio.setNumero(123);
        domicilio.setLocalidad("Springfield");
        domicilio.setProvincia("Springfield");

        paciente = new PacienteDto();
        paciente.setNombre("Pepe");
        paciente.setApellido("Pepardo");
        paciente.setDni(123456789);
        paciente.setDomicilio(domicilio);
    }

    @Test
    public void test01AgregarPaciente() throws Exception {
        PacienteDto p = pacienteService.crear(paciente);
        assertNotNull(pacienteService.buscarPorId(p.getId()));
    }

    @Test
    public void test02ActualizarPaciente() throws Exception {
        PacienteDto p = pacienteService.crear(paciente);
        PacienteDto original = pacienteService.buscarPorId(p.getId());
        p.setNombre("Pepito");
        PacienteDto actualizado = pacienteService.actualizar(p);
        assertNotEquals(actualizado, original);
    }

    @Test
    public void test03EliminarPaciente() throws Exception {
        PacienteDto p = pacienteService.crear(paciente);
        assertNotNull(pacienteService.buscarPorId(p.getId()));
        pacienteService.eliminar(p.getId());
        assertThrows(PacienteServiceException.class, () -> pacienteService.buscarPorId(p.getId()));
    }

    @Test
    public void test04ObtenerTodosLosPacientes() throws Exception {
        pacienteService.crear(paciente);
        assertNotEquals(0, pacienteService.consultarTodos().size());
    }
}
