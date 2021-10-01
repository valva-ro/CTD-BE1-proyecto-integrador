package com.valva.proyectointegrador.persistence.repository;

import com.valva.proyectointegrador.persistence.entities.Turno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ITurnoRepository extends JpaRepository<Turno, Integer> {

    @Query("SELECT t FROM Turno t WHERE t.paciente.nombre=?1 AND t.paciente.apellido=?2 AND t.odontologo.nombre=?3 AND t.odontologo.apellido=?4")
    Optional<List<Turno>> buscar(String nombrePaciente, String apellidoPaciente, String nombreOdontologo, String apellidoOdontologo);

    @Query("SELECT t FROM Turno t WHERE t.odontologo.nombre=?1 AND t.odontologo.apellido=?2")
    Optional<List<Turno>> buscar(String nombreOdontologo, String apellidoOdontologo);

    @Query("SELECT t FROM Turno t WHERE t.odontologo.matricula=?1 AND t.paciente.dni=?2")
    Optional<List<Turno>> buscar(Integer matricula, Integer dni);

}
