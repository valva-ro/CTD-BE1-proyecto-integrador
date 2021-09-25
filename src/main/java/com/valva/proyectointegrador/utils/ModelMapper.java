package com.valva.proyectointegrador.utils;

import java.util.List;
import java.util.stream.Collectors;

public class ModelMapper {

    public static <S, T> T map(org.modelmapper.ModelMapper modelMapper, S element, Class<T> targetClass) {
        return modelMapper.map(element, targetClass);
    }

    public static <S, T> List<T> mapList(org.modelmapper.ModelMapper modelMapper, List<S> source, Class<T> targetClass) {
        return source
                .stream()
                .map(element -> modelMapper.map(element, targetClass))
                .collect(Collectors.toList());
    }
}
