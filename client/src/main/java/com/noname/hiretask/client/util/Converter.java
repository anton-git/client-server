package com.noname.hiretask.client.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * JSON converter.
 */
public class Converter {

    private static Logger log = LoggerFactory.getLogger(Converter.class);

    private final static ObjectMapper mapper = new ObjectMapper();
    static {
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    /**
     * Converts passed object into JSON String
     *
     * @param object object to convert
     * @return JSON String
     */
    public static String toJsonString(final Object object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to convert to JSON", e);
        }
    }

    /**
     * Converts a JSON string into an object of typeReference type
     * @param json JSON String
     * @param typeReference type reference to the type that string should be converted to
     * @param <T> type of result object
     * @return result of converting object to an object of type <code>T</code>
     */
    public static <T> T covertTo(final String json, TypeReference<T> typeReference) {
        try {
            return mapper.readValue(json, typeReference);
        } catch (IOException e) {
            throw new RuntimeException("Failed to convert from JSON", e);
        }
    }

}
