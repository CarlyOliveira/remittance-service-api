package br.com.ctmait.remittanceserviceapi.domain.exceptions;

public class RemittanceEffectivationActionException extends RuntimeException {

    public RemittanceEffectivationActionException(Exception exception){
        super(exception.getMessage(), exception);
    }

}
