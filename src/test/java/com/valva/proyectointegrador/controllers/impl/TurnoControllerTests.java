package com.valva.proyectointegrador.controllers.impl;

import com.valva.proyectointegrador.model.DomicilioDto;
import com.valva.proyectointegrador.model.OdontologoDto;
import com.valva.proyectointegrador.model.PacienteDto;
import com.valva.proyectointegrador.model.TurnoDto;
import com.valva.proyectointegrador.utils.Mapper;
import org.junit.Assert;
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

import java.time.LocalDateTime;

@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class TurnoControllerTests {

    @Autowired
    private MockMvc mockMvc;

    private void registrarOdontologo() throws Exception {
        OdontologoDto o = new OdontologoDto(123456789, "Pepe", "Pepin", 111222);
        this.mockMvc.perform(MockMvcRequestBuilders.post("/odontologos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Mapper.mapObjectToJson(o)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
    }

    private void registrarPaciente() throws Exception {
        PacienteDto p = new PacienteDto("Pepe", "Pepin", 123456789, new DomicilioDto("Calle", 123, "Caballito", "CABA"));
        this.mockMvc.perform(MockMvcRequestBuilders.post("/pacientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Mapper.mapObjectToJson(p)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
    }

    private void cargarDatos() throws Exception {
        registrarPaciente();
        registrarOdontologo();
    }

    @Test
    public void test01registrarTurno() throws Exception {
        cargarDatos();
        PacienteDto p = new PacienteDto();
        p.setId(1);
        OdontologoDto o = new OdontologoDto();
        o.setId(1);
        TurnoDto t = new TurnoDto(LocalDateTime.now(), p, o);
        MvcResult response = this.mockMvc.perform(MockMvcRequestBuilders.post("/turnos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Mapper.mapObjectToJson(t)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        Assert.assertFalse(response.getResponse().getContentAsString().isEmpty());
    }

    @Test
    public void test02noSePuedeRegistrarTurnoSinPacienteUOdontologo() throws Exception {
        TurnoDto t = new TurnoDto();
        t.setFecha(LocalDateTime.now());
        MvcResult response = this.mockMvc.perform(MockMvcRequestBuilders.post("/turnos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Mapper.mapObjectToJson(t)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();

        Assert.assertFalse(response.getResponse().getContentAsString().isEmpty());
    }

    @Test
    public void test03noSePuedeRegistrarTurnoSinLosIdsDePacienteYOdontologo() throws Exception {
        TurnoDto t = new TurnoDto(LocalDateTime.now(), new PacienteDto(), new OdontologoDto());
        MvcResult response = this.mockMvc.perform(MockMvcRequestBuilders.post("/turnos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Mapper.mapObjectToJson(t)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();

        Assert.assertFalse(response.getResponse().getContentAsString().isEmpty());
    }
}
