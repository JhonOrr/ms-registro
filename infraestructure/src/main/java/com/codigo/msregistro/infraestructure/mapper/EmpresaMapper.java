package com.codigo.msregistro.infraestructure.mapper;

import com.codigo.msregistro.domain.aggregates.dto.EmpresaDto;
import com.codigo.msregistro.domain.aggregates.dto.PersonaDto;
import com.codigo.msregistro.infraestructure.entity.EmpresaEntity;
import com.codigo.msregistro.infraestructure.entity.PersonaEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class EmpresaMapper {
    private static final ModelMapper modelMapper = new ModelMapper();

    public EmpresaDto mapToDto(EmpresaEntity entity){
        return modelMapper.map(entity, EmpresaDto.class);
    }
    public EmpresaEntity mapToEntity(EmpresaDto empresaDto){
        return modelMapper.map(empresaDto, EmpresaEntity.class);
    }
}
