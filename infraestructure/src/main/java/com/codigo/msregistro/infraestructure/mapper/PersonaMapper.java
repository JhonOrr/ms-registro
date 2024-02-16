package com.codigo.msregistro.infraestructure.mapper;

import com.codigo.msregistro.domain.aggregates.dto.PersonaDto;
import com.codigo.msregistro.infraestructure.entity.PersonaEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class PersonaMapper {
    private static final ModelMapper modelMapper = new ModelMapper();

    public PersonaDto mapToDto(PersonaEntity entity){
        return modelMapper.map(entity, PersonaDto.class);
    }
    public PersonaEntity mapToEntity(PersonaDto personaDto){
        return modelMapper.map(personaDto, PersonaEntity.class);
    }
}
