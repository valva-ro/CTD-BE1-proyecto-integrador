package com.valva.proyectointegrador.model;

import java.util.Objects;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@ToString
@Getter
@Entity
public class Odontologo {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="odontologo_seq")
    @SequenceGenerator(name = "odontologo_seq", sequenceName = "odontologo_seq", allocationSize=1)
    @Column(name = "odontologo_id")
    private Integer id;

    @Setter
    @Column
    private String nombre;

    @Setter
    @Column
    private String apellido;

    @Setter
    @Column
    private String matricula;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Odontologo that = (Odontologo) o;
        return id.equals(that.id) && nombre.equals(that.nombre) && apellido.equals(that.apellido) && matricula.equals(that.matricula);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, apellido, matricula);
    }
}
