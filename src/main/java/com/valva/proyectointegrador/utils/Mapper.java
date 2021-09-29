package com.valva.proyectointegrador.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.util.List;
import java.util.stream.Collectors;

public class Mapper {

    public static <S, T> T map(org.modelmapper.ModelMapper modelMapper, S element, Class<T> targetClass) {
        return modelMapper.map(element, targetClass);
    }

    public static <S, T> List<T> mapList(org.modelmapper.ModelMapper modelMapper, List<S> source, Class<T> targetClass) {
        return source
                .stream()
                .map(element -> modelMapper.map(element, targetClass))
                .collect(Collectors.toList());
    }

    public static <T> String mapObjectToJson(T t) throws JsonProcessingException {
        return getObjectMapper().writeValueAsString(t);
    }

    public static <T> T mapJsonToObject(String json, Class<T> targetClass) throws JsonProcessingException {
        return getObjectMapper().readValue(json, targetClass);
    }

    private static ObjectMapper getObjectMapper() {
        return new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .registerModule(new JavaTimeModule());
    }
}
