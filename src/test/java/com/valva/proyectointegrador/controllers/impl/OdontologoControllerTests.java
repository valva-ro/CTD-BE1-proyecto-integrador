package com.valva.proyectointegrador.controllers.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.valva.proyectointegrador.model.OdontologoDto;
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
public class OdontologoControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void registrarOdontologo() throws Exception {
        OdontologoDto o = new OdontologoDto(123456789, "Pepe", "Pepin", 123456);
        ObjectMapper objectMapper = new ObjectMapper();
        MvcResult response = this.mockMvc.perform(MockMvcRequestBuilders.post("/odontologos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(o)))
                        .andDo(MockMvcResultHandlers.print())
                        .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        Assert.assertFalse(response.getResponse().getContentAsString().isEmpty());
    }
}
