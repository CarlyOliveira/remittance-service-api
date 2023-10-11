package br.com.ctmait.remittanceserviceapi.domain.exceptions;

public class RemittanceEffectivationActionException extends RuntimeException {

    public RemittanceEffectivationActionException(String error){
        super(error);
    }
    public RemittanceEffectivationActionException(String error, Exception exception){
        super(error, exception);
    }
    public RemittanceEffectivationActionException(Exception exception){
        super(exception.getMessage(), exception);
    }

}
