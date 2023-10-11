package br.com.ctmait.remittanceserviceapi.domain.exceptions;

public class RemittanceQueryActionException extends RuntimeException {

    public RemittanceQueryActionException(String error){
        super(error);
    }
    public RemittanceQueryActionException(String error, Exception exception){
        super(error, exception);
    }
    public RemittanceQueryActionException(Exception exception){
        super(exception.getMessage(), exception);
    }

}
