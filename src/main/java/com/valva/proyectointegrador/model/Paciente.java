package com.valva.proyectointegrador.model;

import java.time.LocalDate;
import java.util.Objects;

public class Paciente {

    private Integer id;
    private String nombre;
    private String apellido;
    private Integer dni;
    private LocalDate fechaIngreso;
    private Domicilio domicilio;

    public Paciente() {}

    public Paciente(String nombre, String apellido, Integer dni, LocalDate fechaIngreso, Domicilio domicilio) {
        this.id = null;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.fechaIngreso = fechaIngreso;
        this.domicilio = domicilio;
    }

    public Paciente(Integer id, String nombre, String apellido, Integer dni, LocalDate fechaIngreso, Domicilio domicilio) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.fechaIngreso = fechaIngreso;
        this.domicilio = domicilio;
    }

    public Paciente(String nombre, String apellido, Integer dni, LocalDate fechaIngreso) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.fechaIngreso = fechaIngreso;
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public Integer getDni() {
        return dni;
    }

    public void setDni(Integer dni) {
        this.dni = dni;
    }

    public LocalDate getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(LocalDate fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public Domicilio getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(Domicilio domicilio) {
        this.domicilio = domicilio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Paciente paciente = (Paciente) o;
        return id.equals(paciente.id) &&
               Objects.equals(dni, paciente.dni) &&
               Objects.equals(domicilio, paciente.domicilio) &&
               nombre.equals(paciente.nombre) &&
               apellido.equals(paciente.apellido) &&
               fechaIngreso.equals(paciente.fechaIngreso);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, apellido, dni, fechaIngreso, domicilio);
    }

    @Override
    public String toString() {
        String informacion = "Paciente{" +
                "pacienteID=" + id +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", dni=" + dni +
                ", fechaIngreso=" + fechaIngreso;
        if (domicilio != null &&
                domicilio.getCalle() != null && domicilio.getNumero() != null &&
                domicilio.getLocalidad() != null && domicilio.getProvincia() != null) {
            informacion += ", domicilio=" + domicilio;
        }
        informacion += '}';
        return informacion;
    }
}
