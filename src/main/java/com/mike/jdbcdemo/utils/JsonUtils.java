package com.mike.jdbcdemo.utils;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonUtils {

    private final static Logger logger = LoggerFactory.getLogger(JsonUtils.class);

    public static <POJO> POJO getObject(String json, final Class<POJO> clazz) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        try {
            mapper.setVisibility(mapper.getSerializationConfig().getDefaultVisibilityChecker().withFieldVisibility(JsonAutoDetect.Visibility.ANY).withGetterVisibility(JsonAutoDetect.Visibility.NONE).withSetterVisibility(
                    JsonAutoDetect.Visibility.NONE).withCreatorVisibility(JsonAutoDetect.Visibility.NONE));
            return mapper.readValue(json, clazz);
        }
        catch (Exception e) {
            logger.error("JsonReader Error", e);
            return null;
        }
    }
    public static <POJO> String getJson(POJO object) {

        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.setVisibility(mapper.getSerializationConfig().getDefaultVisibilityChecker().withFieldVisibility(JsonAutoDetect.Visibility.ANY).withGetterVisibility(JsonAutoDetect.Visibility.NONE).withSetterVisibility(
                    JsonAutoDetect.Visibility.NONE).withCreatorVisibility(JsonAutoDetect.Visibility.NONE));
            mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

            return mapper.writeValueAsString(object);
        }
        catch (Exception e) {
            e.printStackTrace();
            logger.error("JsonWriter Error", e);
            return null;
        }
    }
}
