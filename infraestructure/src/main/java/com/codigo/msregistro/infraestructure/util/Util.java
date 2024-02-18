package com.codigo.msregistro.infraestructure.util;

import com.codigo.msregistro.domain.aggregates.dto.PersonaDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class Util {
    public static String convertToJson(PersonaDto personaDto){
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(personaDto);
        } catch (JsonProcessingException e){
            throw new RuntimeException(e);
        }
    }

    public static <T> T convertFromJson(String json, Class<T> valueType){
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(json, valueType);
        } catch(JsonProcessingException e){
            throw new RuntimeException(e);
        }
    }
}
