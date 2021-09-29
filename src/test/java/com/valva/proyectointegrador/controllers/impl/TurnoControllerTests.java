package com.valva.proyectointegrador.controllers.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.valva.proyectointegrador.model.DomicilioDto;
import com.valva.proyectointegrador.model.OdontologoDto;
import com.valva.proyectointegrador.model.PacienteDto;
import com.valva.proyectointegrador.model.TurnoDto;
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

import java.time.LocalDateTime;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class TurnoControllerTests {

    @Autowired
    private MockMvc mockMvc;

    private void registrarOdontologo() throws Exception {
        OdontologoDto o = new OdontologoDto(123456789, "Pepe", "Pepin", 123456);
        ObjectMapper objectMapper = new ObjectMapper();
        this.mockMvc.perform(MockMvcRequestBuilders.post("/odontologos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(o)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
    }

    private void registrarPaciente() throws Exception {
        PacienteDto p = new PacienteDto("Pepe", "Pepin", 123456789, new DomicilioDto("Calle", 123, "Caballito", "CABA"));
        ObjectMapper objectMapper = new ObjectMapper();
        this.mockMvc.perform(MockMvcRequestBuilders.post("/pacientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(p)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
    }

    @Test
    public void registrarTurno() throws Exception {
        registrarPaciente();
        registrarOdontologo();
        PacienteDto p = new PacienteDto();
        p.setId(1);
        OdontologoDto o = new OdontologoDto();
        o.setId(1);
        TurnoDto t = new TurnoDto(LocalDateTime.now(), p, o);
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        MvcResult response = this.mockMvc.perform(MockMvcRequestBuilders.post("/turnos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(t)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        Assert.assertFalse(response.getResponse().getContentAsString().isEmpty());
    }

    @Test
    public void noSePuedeRegistrarTurnoSinPacienteUOdontologo() throws Exception {
        TurnoDto t = new TurnoDto();
        t.setFecha(LocalDateTime.now());
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        MvcResult response = this.mockMvc.perform(MockMvcRequestBuilders.post("/turnos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(t)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();

        Assert.assertFalse(response.getResponse().getContentAsString().isEmpty());
    }

    @Test
    public void noSePuedeRegistrarTurnoSinLosIdsDePacienteYOdontologo() throws Exception {
        TurnoDto t = new TurnoDto(LocalDateTime.now(), new PacienteDto(), new OdontologoDto());
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        MvcResult response = this.mockMvc.perform(MockMvcRequestBuilders.post("/turnos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(t)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();

        Assert.assertFalse(response.getResponse().getContentAsString().isEmpty());
    }
}
