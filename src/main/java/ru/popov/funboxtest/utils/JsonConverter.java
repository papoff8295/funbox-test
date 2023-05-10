package ru.popov.funboxtest.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.r2dbc.postgresql.codec.Json;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class JsonConverter {

    private final ObjectMapper objectMapper;

    public Json toJson(Object object) {
        try {
            return Json.of(objectMapper.writeValueAsString(object));
        } catch (JsonProcessingException e) {
            throw new RuntimeException();
        }
    }

    public JsonNode toJsonNode(Object object) {
        return objectMapper.valueToTree(object);
    }

    public JsonNode toJsonNode(String json) {
        try {
            return objectMapper.readTree(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException();
        }
    }

    public <T> T fromJsonNode(JsonNode jsonNode, Class<T> resultClass) {
        try {
            return objectMapper.treeToValue(jsonNode, resultClass);
        } catch (JsonProcessingException e) {
            throw new RuntimeException();
        }
    }

    public <T> T fromJsonString(String json, Class<T> resultClass) {
        try {
            return objectMapper.readValue(json, resultClass);
        } catch (JsonProcessingException e) {
            throw new RuntimeException();
        }
    }
}
