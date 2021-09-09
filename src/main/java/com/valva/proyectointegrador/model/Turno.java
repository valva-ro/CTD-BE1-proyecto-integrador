package com.valva.proyectointegrador.model;

import java.time.LocalDate;

public class Turno {

    private Integer id;
    private Integer idPaciente;
    private Integer idOdontologo;
    private LocalDate fecha;

    public Turno() {}

    public Turno(Integer id, Integer idPaciente, Integer idOdontologo, LocalDate fecha) {
        this.id = id;
        this.idPaciente = idPaciente;
        this.idOdontologo = idOdontologo;
        this.fecha = fecha;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(Integer idPaciente) {
        this.idPaciente = idPaciente;
    }

    public Integer getIdOdontologo() {
        return idOdontologo;
    }

    public void setIdOdontologo(Integer idOdontologo) {
        this.idOdontologo = idOdontologo;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        return "Turno{" +
                "id=" + id +
                ", idPaciente=" + idPaciente +
                ", idOdontologo=" + idOdontologo +
                ", fecha=" + fecha +
                '}';
    }
}
