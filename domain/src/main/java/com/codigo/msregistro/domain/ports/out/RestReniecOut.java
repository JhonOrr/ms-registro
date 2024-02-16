package com.codigo.msregistro.domain.ports.out;

import com.codigo.msregistro.domain.aggregates.response.ResponseReniec;

public interface RestReniecOut {
    //ejecutar a reniec, este puerto lo implementa en infraestructura
    ResponseReniec getInfoReniec(String numDoc);
}
