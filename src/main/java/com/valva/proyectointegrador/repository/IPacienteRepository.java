package com.valva.proyectointegrador.repository;

import com.valva.proyectointegrador.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPacienteRepository extends JpaRepository<Paciente, Integer>{
}
