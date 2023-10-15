package br.com.ctmait.remittanceserviceapi.domain.exceptions;

public class RemittanceAlreadyExistsException extends RuntimeException {

    public RemittanceAlreadyExistsException(String error){
        super(error);
    }
}
