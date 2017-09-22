package com.noname.hiretask.server.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;

/**
 * JSON converter.
 * Used for converting requests from client which use JSON format into objects which server knows
 * and for converting server objects to JSON format when sending responses.
 */
public class JsonConverter {

    private final static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        OBJECT_MAPPER.registerModule(new JavaTimeModule());
        OBJECT_MAPPER.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    /**
     * Converts object to JSON string
     *
     * @param object object to convert
     * @return JSON string
     * @throws JsonProcessingException
     */
    public static String toJson(final Object object) throws JsonProcessingException {
        return OBJECT_MAPPER.writeValueAsString(object);
    }

    /**
     * Convert JSON string into object of type <code>T</code>
     *
     * @param jsonString source JSON string
     * @param clazz      class of type <code>T</code>
     * @param <T>        type of result object
     * @return result of converting JSON string to object of type <code>T</code>
     * @throws IOException
     */
    public static <T> T fromJson(final String jsonString, final Class<T> clazz) throws IOException {
        return OBJECT_MAPPER.readValue(jsonString, clazz);
    }
}
