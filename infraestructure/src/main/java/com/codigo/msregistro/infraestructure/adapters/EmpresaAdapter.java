package com.codigo.msregistro.infraestructure.adapters;

import com.codigo.msregistro.domain.aggregates.constants.Constants;
import com.codigo.msregistro.domain.aggregates.dto.EmpresaDto;
import com.codigo.msregistro.domain.aggregates.request.RequestEmpresa;
import com.codigo.msregistro.domain.aggregates.response.ResponseSunat;
import com.codigo.msregistro.domain.ports.out.EmpresaServiceOut;
import com.codigo.msregistro.infraestructure.entity.EmpresaEntity;
import com.codigo.msregistro.infraestructure.entity.TipoDocumentoEntity;
import com.codigo.msregistro.infraestructure.mapper.EmpresaMapper;
import com.codigo.msregistro.infraestructure.repository.EmpresaRepository;
import com.codigo.msregistro.infraestructure.repository.TipoDocumentoRepository;
import com.codigo.msregistro.infraestructure.rest.client.ClienteSunat;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmpresaAdapter implements EmpresaServiceOut {

    private final EmpresaRepository empresaRepository;
    private final TipoDocumentoRepository tipoDocumentoRepository;
    private final EmpresaMapper empresaMapper;
    private final ClienteSunat clienteSunat;

    @Value("${token.api}")
    private String tokenApi;




    @Override
    public EmpresaDto crearEmpresaOut(RequestEmpresa requestEmpresa) {
        ResponseSunat datosSunat = getExecutionSunat(requestEmpresa.getNumDoc());
        empresaRepository.save(getEntity(datosSunat, requestEmpresa));
        return empresaMapper.mapToDto(getEntity(datosSunat, requestEmpresa));


    }

    @Override
    public Optional<EmpresaDto> obtenerEmpresaOut(Long id) {
        return Optional.ofNullable(empresaMapper.mapToDto(empresaRepository.findById(id).get()));
    }

    @Override
    public List<EmpresaDto> obtenerTodosOut() {
        List<EmpresaDto> empresaDtoList = new ArrayList<>();
        List<EmpresaEntity> entities = empresaRepository.findAll();
        for(EmpresaEntity empresa : entities){
            EmpresaDto empresaDto = empresaMapper.mapToDto(empresa);
            empresaDtoList.add(empresaDto);
        }
        return empresaDtoList;
    }

    @Override
    public EmpresaDto actualizarOut(Long id, RequestEmpresa requestEmpresa) {

        boolean existe = empresaRepository.existsById(id);
        if(existe){
            Optional<EmpresaEntity> entity = empresaRepository.findById(id);
            ResponseSunat responseSunat = getExecutionSunat(requestEmpresa.getNumDoc());
            empresaRepository.save(getEntityUpdate(responseSunat,entity.get()));
            return empresaMapper.mapToDto(getEntityUpdate(responseSunat,entity.get()));
        }
        return null;
    }

    @Override
    public EmpresaDto deleteOut(Long id) {
        return null;
    }

    public ResponseSunat getExecutionSunat(String ruc){
        String authorization = "Bearer " + tokenApi;
        ResponseSunat responseSunat = clienteSunat.getInfoSunat(ruc, authorization);
        return responseSunat;
    }

    private EmpresaEntity getEntity(ResponseSunat sunat, RequestEmpresa requestEmpresa){
        TipoDocumentoEntity tipoDocumento = tipoDocumentoRepository.findByCodTipo(requestEmpresa.getTipoDoc());
        EmpresaEntity entity = new EmpresaEntity();
        entity.setNumDocu(sunat.getNumeroDocumento());
        entity.setRazonSocial(sunat.getRazonSocial());
        entity.setNomComercial(sunat.getRazonSocial());
        entity.setEstado(Constants.STATUS_ACTIVE);
        entity.setUsuaCrea(Constants.AUDIT_ADMIN);
        entity.setDateCreate(getTimestamp());
        entity.setTipoDocumento(tipoDocumento);
        return entity;
    }

    private Timestamp getTimestamp(){
        long currentTime = System.currentTimeMillis();
        Timestamp timestamp = new Timestamp(currentTime);
        return timestamp;
    }

    private EmpresaEntity getEntityUpdate(ResponseSunat sunat, EmpresaEntity empresaActualizar){
        empresaActualizar.setRazonSocial(sunat.getRazonSocial());
        empresaActualizar.setNomComercial(sunat.getRazonSocial());
        return empresaActualizar;
    }
}
