package org.search.framework.core.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Optional;

public enum JsonConverter {
    INSTANCE;

    private ObjectMapper mapper = new ObjectMapper();

    public <T> Optional<String> getJson(T object) {
        try {
            return Optional.ofNullable(mapper.writeValueAsString(object));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> Optional<T> getObject(String json, Class<T> clazz) {
        try {
            return Optional.ofNullable(mapper.readValue(json, clazz));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
