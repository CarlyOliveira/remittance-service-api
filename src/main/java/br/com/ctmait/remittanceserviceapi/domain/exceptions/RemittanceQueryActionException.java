package br.com.ctmait.remittanceserviceapi.domain.exceptions;

public class RemittanceQueryActionException extends RuntimeException {

    public RemittanceQueryActionException(Exception exception){
        super(exception.getMessage(), exception);
    }

}
