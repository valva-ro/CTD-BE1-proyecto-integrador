package com.valva.proyectointegrador.persistence.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Entity
@Table(name = "odontologos")
public class Odontologo {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "odontologo_seq")
    @SequenceGenerator(name = "odontologo_seq", sequenceName = "odontologo_seq", allocationSize = 1)
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
    private Integer matricula;

    @OneToMany(mappedBy = "odontologo", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Turno> turnos = new HashSet<>();

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
