package com.alura.literalura.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConvierteDatos implements IConvierteDatos {
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public <T> T obtenerDatos(String json, Class<T> clase) {
        try {
           // System.out.println(objectMapper.readTree(json).get("results").get(0) );
           // String objeto =objectMapper.readTree(json).get("results").get(0).asText();
            return objectMapper.readValue( json,clase);
        } catch (JsonProcessingException e) {
            System.out.println("error al convertir datos");
            throw new RuntimeException(e);
        }
    }
}
