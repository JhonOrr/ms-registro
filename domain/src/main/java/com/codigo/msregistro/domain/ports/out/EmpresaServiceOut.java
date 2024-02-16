package com.codigo.msregistro.domain.ports.out;

import com.codigo.msregistro.domain.aggregates.dto.EmpresaDto;
import com.codigo.msregistro.domain.aggregates.request.RequestEmpresa;

import java.util.List;
import java.util.Optional;

public interface EmpresaServiceOut {
    EmpresaDto crearEmpresaOut(RequestEmpresa requestEmpresa);
    Optional<EmpresaDto> obtenerEmpresaOut(Long id);
    List<EmpresaDto> obtenerTodosOut();
    EmpresaDto actualizarOut(Long Id, RequestEmpresa requestEmpresa);
    EmpresaDto deleteOut(Long id);

}
