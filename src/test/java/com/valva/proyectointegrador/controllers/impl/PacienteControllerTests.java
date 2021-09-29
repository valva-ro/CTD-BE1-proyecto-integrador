package com.valva.proyectointegrador.controllers.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.valva.proyectointegrador.model.DomicilioDto;
import com.valva.proyectointegrador.model.PacienteDto;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
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

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class PacienteControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void registrarPaciente() throws Exception {
        PacienteDto p = new PacienteDto("Pepe", "Pepin", 123456789, new DomicilioDto("Calle", 123, "Caballito", "CABA"));
        ObjectMapper objectMapper = new ObjectMapper();
        MvcResult response = this.mockMvc.perform(MockMvcRequestBuilders.post("/pacientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(p)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        Assert.assertFalse(response.getResponse().getContentAsString().isEmpty());
    }

}
