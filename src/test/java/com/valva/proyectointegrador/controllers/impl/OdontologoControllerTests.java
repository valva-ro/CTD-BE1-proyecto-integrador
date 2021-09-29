package com.valva.proyectointegrador.controllers.impl;

import com.valva.proyectointegrador.model.OdontologoDto;
import com.valva.proyectointegrador.utils.Mapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class OdontologoControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Before
    public void cargarDatos() throws Exception {
        OdontologoDto o1 = new OdontologoDto(123456788, "Pepe", "Pepardo", 123455);
        OdontologoDto o2 = new OdontologoDto(123456787, "Pepa", "Pepardo", 123454);
        mockMvc.perform(MockMvcRequestBuilders.post("/pacientes").contentType(MediaType.APPLICATION_JSON).content(Mapper.mapObjectToJson(o1)));
        mockMvc.perform(MockMvcRequestBuilders.post("/pacientes").contentType(MediaType.APPLICATION_JSON).content(Mapper.mapObjectToJson(o2)));
    }

    @Test
    public void test01registrarOdontologo() throws Exception {
        OdontologoDto o = new OdontologoDto(123456789, "Pepe", "Pepin", 123456);
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.post("/odontologos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Mapper.mapObjectToJson(o)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        Assert.assertFalse(response.getResponse().getContentAsString().isEmpty());
    }

    @Test
    public void test02buscarOdontologoPorIdInexistenteDevuelveNotFound() throws Exception {
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.get("/odontologos/{id}", "10"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andReturn();
        assertEquals("El odontólogo no existe", response.getResponse().getContentAsString());
    }

    @Test
    public void test03buscarOdontologoPorIdExistenteDevuelveOk() throws Exception {
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.get("/odontologos/{id}", "1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        Assert.assertFalse(response.getResponse().getContentAsString().isEmpty());
    }

    @Test
    public void test04buscarOdontologosPorNombreInexistenteDevuelveOkYArrayVacio() throws Exception {
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders
                        .get("/odontologos")
                        .param("nombre", "José")
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        assertEquals("[]", response.getResponse().getContentAsString());
    }

    @Test
    public void test05buscarOdontologosPorNombreExistenteDevuelveOkYArrayNoVacio() throws Exception {
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders
                        .get("/odontologos")
                        .param("nombre", "Pepe")
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        assertNotEquals("[]", response.getResponse().getContentAsString());
    }

    @Test
    public void test06buscarOdontologosPorNombreYApellidoInexistenteDevuelveOkYArrayVacio() throws Exception {
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders
                        .get("/odontologos")
                        .param("nombre", "José")
                        .param("apellido", "Pérez")
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        assertEquals("[]", response.getResponse().getContentAsString());
    }

    @Test
    public void test07buscarOdontologoPorMatriculaExistenteDevuelveOk() throws Exception {
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders
                        .get("/odontologos")
                        .param("matricula", "123456")
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        assertNotEquals("", response.getResponse().getContentAsString());
    }

    @Test
    public void test08ActualizarOdontologoInexistenteDevuelveNotFound() throws Exception {
        OdontologoDto o = new OdontologoDto(123456789, "Pepe", "Pepin", 123456);
        o.setId(99);
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/odontologos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Mapper.mapObjectToJson(o))
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void test09ActualizarOdontologoExistenteDevuelveOk() throws Exception {
        OdontologoDto o = new OdontologoDto(123456789, "Pepe", "Pepin", 123456);
        o.setId(1);
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders
                        .put("/odontologos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Mapper.mapObjectToJson(o))
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        assertEquals(Mapper.mapObjectToJson(o), response.getResponse().getContentAsString());
    }

    @Test
    public void test10eliminarOdontologoPorIdInexistenteDevuelveNotFound() throws Exception {
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.delete("/odontologos/{id}", "999"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andReturn();
        assertEquals("No existe ningún odontólogo con id: 999", response.getResponse().getContentAsString());
    }

    @Test
    public void test11eliminarOdontologoPorIdExistenteDevuelveOk() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/odontologos/{id}", "2"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    @Test
    public void test12buscarTodosLosOdontologos() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/odontologos"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }
}
