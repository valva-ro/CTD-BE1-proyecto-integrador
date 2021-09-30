package com.valva.proyectointegrador.controllers.impl;

import com.valva.proyectointegrador.exceptions.GlobalExceptionsHandler;
import com.valva.proyectointegrador.model.DomicilioDto;
import com.valva.proyectointegrador.model.PacienteDto;
import com.valva.proyectointegrador.utils.Mapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc(addFilters = false)
@WebAppConfiguration
public class PacienteControllerTests {

    @Autowired
    private PacienteController pacienteController;

    private MockMvc mockMvc;

    @Before
    public void reset() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(pacienteController).setControllerAdvice(GlobalExceptionsHandler.class).build();
        PacienteDto p1 = new PacienteDto("Pepe", "Pepardo", 111111111, new DomicilioDto("Calle", 123, "Caballito", "CABA"));
        PacienteDto p2 = new PacienteDto("Pepa", "Peparda", 111111110, new DomicilioDto("Lleca", 321, "Caballito", "CABA"));
        mockMvc.perform(MockMvcRequestBuilders.post("/pacientes").contentType(MediaType.APPLICATION_JSON).content(Mapper.mapObjectToJson(p1)));
        mockMvc.perform(MockMvcRequestBuilders.post("/pacientes").contentType(MediaType.APPLICATION_JSON).content(Mapper.mapObjectToJson(p2)));
    }

    @Test
    public void test01registrarPacientes() throws Exception {
        PacienteDto p = new PacienteDto("Pepe", "Pepin", 123456789, new DomicilioDto("Calle", 123, "Caballito", "CABA"));
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.post("/pacientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Mapper.mapObjectToJson(p)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        Assert.assertFalse(response.getResponse().getContentAsString().isEmpty());
    }

    @Test
    public void test02buscarPacientePorIdInexistenteDevuelveNotFound() throws Exception {
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.get("/pacientes/{id}", "10"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andReturn();
        assertEquals("No se encontró el paciente con id 10", response.getResponse().getContentAsString());
    }

    @Test
    public void test03buscarPacientePorIdExistenteDevuelveOk() throws Exception {
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.get("/pacientes/{id}", "1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        Assert.assertFalse(response.getResponse().getContentAsString().isEmpty());
    }

    @Test
    public void test04buscarPacientesPorNombreInexistenteDevuelveOkYArrayVacio() throws Exception {
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders
                        .get("/pacientes")
                        .param("nombre", "José")
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        assertEquals("[]", response.getResponse().getContentAsString());
    }

    @Test
    public void test05buscarPacientesPorNombreExistenteDevuelveOkYArrayNoVacio() throws Exception {
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders
                        .get("/pacientes")
                        .param("nombre", "Pepe")
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        assertNotEquals("[]", response.getResponse().getContentAsString());
    }

    @Test
    public void test06buscarPacientesPorNombreYApellidoInexistenteDevuelveOkYArrayVacio() throws Exception {
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders
                        .get("/pacientes")
                        .param("nombre", "José")
                        .param("apellido", "Pérez")
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        assertEquals("[]", response.getResponse().getContentAsString());
    }

    @Test
    public void test07buscarPacientePorMatriculaExistenteDevuelveOk() throws Exception {
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders
                        .get("/pacientes")
                        .param("matricula", "123456")
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        assertNotEquals("[]", response.getResponse().getContentAsString());
    }

    @Test
    public void test08ActualizarPacienteInexistenteDevuelveNotFound() throws Exception {
        PacienteDto p = new PacienteDto("Pepe", "Pepardo", 111111111, new DomicilioDto("Calle", 123, "Caballito", "CABA"));
        p.setId(99);
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/pacientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Mapper.mapObjectToJson(p))
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void test09ActualizarPacienteExistenteDevuelveOk() throws Exception {
        DomicilioDto d =  new DomicilioDto("Calle", 111, "Caballito", "CABA");
        d.setId(1);
        PacienteDto p = new PacienteDto("Pepe", "Pepardisimo", 111111111, d);
        p.setId(1);
        p.setFechaIngreso(LocalDate.now());
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders
                        .put("/pacientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Mapper.mapObjectToJson(p))
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        Assert.assertFalse(response.getResponse().getContentAsString().isEmpty());
    }

    @Test
    public void test10eliminarPacientePorIdInexistenteDevuelveNotFound() throws Exception {
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.delete("/pacientes/{id}", "999"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andReturn();
        assertEquals("No existe ningún paciente con id: 999", response.getResponse().getContentAsString());
    }

    @Test
    public void test00eliminarPacientePorIdExistenteDevuelveOk() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/pacientes/{id}", "2"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    @Test
    public void test12buscarTodosLosPacientes() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/pacientes"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

}
