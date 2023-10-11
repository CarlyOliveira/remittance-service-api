package br.com.ctmait.remittanceserviceapi.tech.rest.exceptions.service;

import br.com.ctmait.remittanceserviceapi.tech.rest.exceptions.payload.ExceptionPayload;

public interface ExceptionService {

    ExceptionPayload generatePayload(Exception ex);
}
