package com.valva.proyectointegrador.service.impl;

import com.valva.proyectointegrador.config.SpringConfig;
import com.valva.proyectointegrador.exceptions.service.OdontologoServiceException;
import com.valva.proyectointegrador.model.OdontologoDto;
import com.valva.proyectointegrador.persistence.entities.Odontologo;
import com.valva.proyectointegrador.persistence.repository.IOdontologoRepository;
import com.valva.proyectointegrador.service.IOdontologoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OdontologoService implements IOdontologoService {

    @Autowired
    private IOdontologoRepository odontologoRepository;
    @Autowired
    private SpringConfig springConfig;

    @Override
    public OdontologoDto buscar(Integer matricula) throws OdontologoServiceException {
        Odontologo odontologo = odontologoRepository.buscar(matricula).orElse(null);
        if (odontologo == null)
            throw new OdontologoServiceException("No se encontró el odontólogo con matricula " + matricula);
        return springConfig.getModelMapper().map(odontologo, OdontologoDto.class);
    }

    @Override
    public List<OdontologoDto> buscar(String nombre) {
        List<Odontologo> odontologos = odontologoRepository.buscar(nombre).orElse(new ArrayList<>());
        return springConfig.getModelMapper().map(odontologos, List.class);
    }

    @Override
    public List<OdontologoDto> buscar(String nombre, String apellido) {
        List<Odontologo> odontologos = odontologoRepository.buscar(nombre, apellido).orElse(new ArrayList<>());
        return springConfig.getModelMapper().map(odontologos, List.class);
    }

    @Override
    public OdontologoDto buscarPorId(Integer id) throws OdontologoServiceException {
        Odontologo odontologo = odontologoRepository.findById(id).orElse(null);
        if (odontologo == null)
            throw new OdontologoServiceException("No se encontró el odontólogo con id " + id);
        return springConfig.getModelMapper().map(odontologo, OdontologoDto.class);
    }

    @Override
    public OdontologoDto crear(OdontologoDto odontologoDto) throws OdontologoServiceException {
        if (odontologoDto == null)
            throw new OdontologoServiceException("No se pudo guardar el odontologo " + odontologoDto);
        Odontologo odontologo = springConfig.getModelMapper().map(odontologoDto, Odontologo.class);
        return springConfig.getModelMapper().map(odontologoRepository.save(odontologo), OdontologoDto.class);
    }

    @Override
    public OdontologoDto actualizar(OdontologoDto odontologoDto) throws OdontologoServiceException {
        OdontologoDto odontologoActualizado = null;
        if (odontologoDto == null)
            throw new OdontologoServiceException("No se pudo actualizar el odontologo null");
        if (odontologoDto.getId() == null)
            throw new OdontologoServiceException("El id del odontologo a actualizar no puede ser null");
        Optional<Odontologo> odontologoEnBD = odontologoRepository.findById(odontologoDto.getId());
        if (odontologoEnBD.isPresent()) {
            Odontologo actualizado = this.actualizar(odontologoEnBD.get(), odontologoDto);
            Odontologo guardado = odontologoRepository.save(actualizado);
            odontologoActualizado = springConfig.getModelMapper().map(guardado, OdontologoDto.class);
        } else {
            throw new OdontologoServiceException("El odontologo no existe");
        }
        return odontologoActualizado;
    }

    @Override
    public void eliminar(Integer id) {
        odontologoRepository.deleteById(id);
    }

    @Override
    public List<OdontologoDto> consultarTodos() {
        List<Odontologo> odontologos = odontologoRepository.findAll();
        return springConfig.getModelMapper().map(odontologos, List.class);
    }

    private Odontologo actualizar(Odontologo odontologo, OdontologoDto odontologoDto) {
        if (odontologoDto.getNombre() != null) {
            odontologo.setNombre(odontologoDto.getNombre());
        }
        if (odontologoDto.getApellido() != null) {
            odontologo.setApellido(odontologoDto.getApellido());
        }
        if (odontologoDto.getMatricula() != null) {
            odontologo.setMatricula(odontologoDto.getMatricula());
        }
        return odontologo;
    }
}
