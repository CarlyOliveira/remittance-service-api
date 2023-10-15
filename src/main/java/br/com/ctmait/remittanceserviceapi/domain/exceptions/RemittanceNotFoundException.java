package br.com.ctmait.remittanceserviceapi.domain.exceptions;

public class RemittanceNotFoundException extends RuntimeException {

    public RemittanceNotFoundException(String error){
        super(error);
    }
}
