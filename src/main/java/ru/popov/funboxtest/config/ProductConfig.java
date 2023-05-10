package ru.popov.funboxtest.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import ru.popov.funboxtest.utils.JsonConverter;

@Configuration
public class ProductConfig {

    @Bean
    public ObjectMapper customObjectMapper(Jackson2ObjectMapperBuilder builder) {
        return builder.build()
                .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Bean
    public JsonConverter jsonConverter(ObjectMapper objectMapper) {
        return new JsonConverter(objectMapper);
    }
}
