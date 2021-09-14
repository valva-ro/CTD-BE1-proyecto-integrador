package com.valva.proyectointegrador.model;

import java.time.LocalDate;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@ToString
@Getter
@Entity
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="paciente_seq")
    @SequenceGenerator(name = "paciente_seq", sequenceName = "paciente_seq", allocationSize=1)
    @Column(name = "paciente_id")
    private Integer id;

    @Setter
    @Column
    private String nombre;

    @Setter
    @Column
    private String apellido;

    @Setter
    @Column
    private Integer dni;

    @Setter
    @Column
    private LocalDate fechaIngreso;

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "domicilio_id")
    private Domicilio domicilio;

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
}
