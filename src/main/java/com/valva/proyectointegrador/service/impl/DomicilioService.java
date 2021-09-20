package com.valva.proyectointegrador.service.impl;

import com.valva.proyectointegrador.config.SpringConfig;
import com.valva.proyectointegrador.exceptions.service.DomicilioServiceException;
import com.valva.proyectointegrador.model.DomicilioDto;
import com.valva.proyectointegrador.persistence.entities.Domicilio;
import com.valva.proyectointegrador.persistence.repository.IDomicilioRepository;
import com.valva.proyectointegrador.service.IDomicilioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DomicilioService implements IDomicilioService {

    @Autowired
    private IDomicilioRepository domicilioRepository;
    @Autowired
    private SpringConfig springConfig;

    public List<DomicilioDto> buscar(String calle) {
        List<Domicilio> domicilios = domicilioRepository.buscar(calle).orElse(new ArrayList<>());
        return springConfig.getModelMapper().map(domicilios, List.class);
    }

    public List<DomicilioDto> buscar(String calle, Integer numero) {
        List<Domicilio> domicilios = domicilioRepository.buscar(calle, numero).orElse(new ArrayList<>());
        return springConfig.getModelMapper().map(domicilios, List.class);
    }

    public DomicilioDto buscar(String calle, Integer numero, String localidad, String provincia) throws Exception {
        Domicilio domicilio = domicilioRepository.buscar(calle, numero, localidad, provincia).orElse(null);
        if (domicilio == null)
            throw new DomicilioServiceException("No se encontró el domicilio");

        return springConfig.getModelMapper().map(domicilio, DomicilioDto.class);
    }

    @Override
    public DomicilioDto buscarPorId(Integer id) throws Exception {
        Domicilio domicilio = domicilioRepository.findById(id).orElse(null);
        if (domicilio == null)
            throw new DomicilioServiceException("No se encontró el domicilio con id " + id);

        return springConfig.getModelMapper().map(domicilio, DomicilioDto.class);
    }

    @Override
    public DomicilioDto crear(DomicilioDto domicilioDto) throws Exception {
        if (domicilioDto == null)
            throw new DomicilioServiceException("No se pudo guardar el domicilio " + domicilioDto);

        Domicilio domicilio = springConfig.getModelMapper().map(domicilioDto, Domicilio.class);
        domicilioDto = springConfig.getModelMapper().map(domicilioRepository.save(domicilio), DomicilioDto.class);
        return domicilioDto;
    }

    @Override
    public DomicilioDto actualizar(DomicilioDto domicilioDto) throws Exception {
        DomicilioDto domicilioActualizado = null;
        if (domicilioDto == null)
            throw new DomicilioServiceException("No se pudo actualizar el domicilio null");
        if (domicilioDto.getId() == null)
            throw new DomicilioServiceException("El id del domicilio a actualizar no puede ser null");

        Optional<Domicilio> domicilioEnBD = domicilioRepository.findById(domicilioDto.getId());
        if (domicilioEnBD.isPresent()) {
            Domicilio actualizado = this.actualizar(domicilioEnBD.get(), domicilioDto);
            Domicilio guardado = domicilioRepository.save(actualizado);
            domicilioActualizado = springConfig.getModelMapper().map(guardado, DomicilioDto.class);
        }
        return domicilioActualizado;
    }

    @Override
    public void eliminar(Integer id) {
        domicilioRepository.deleteById(id);
    }

    @Override
    public List<DomicilioDto> consultarTodos() {
        List<Domicilio> domicilios = domicilioRepository.findAll();
        return springConfig.getModelMapper().map(domicilios, List.class);
    }

    private Domicilio actualizar(Domicilio domicilio, DomicilioDto domicilioDto) {
        if (domicilioDto.getCalle() != null) {
            domicilio.setCalle(domicilioDto.getCalle());
        }
        if (domicilioDto.getNumero() != null) {
            domicilio.setNumero(domicilioDto.getNumero());
        }
        if (domicilioDto.getLocalidad() != null) {
            domicilio.setLocalidad(domicilioDto.getLocalidad());
        }
        if (domicilioDto.getProvincia() != null) {
            domicilio.setProvincia(domicilioDto.getProvincia());
        }
        return domicilio;
    }
}
