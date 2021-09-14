package com.valva.proyectointegrador.model;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@ToString
@Getter
@Entity
@Table(name = "turnos")
public class Turno {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="turno_seq")
    @SequenceGenerator(name = "turno_seq", sequenceName = "turno_seq", allocationSize=1)
    @Column(name = "turno_id")
    private Integer id;

    @Column
    private Integer idPaciente;

    @Column
    private Integer idOdontologo;

    @Setter
    @Column
    private LocalDate fecha;
}
