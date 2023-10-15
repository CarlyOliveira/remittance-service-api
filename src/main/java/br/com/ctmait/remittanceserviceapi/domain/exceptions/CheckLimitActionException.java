package br.com.ctmait.remittanceserviceapi.domain.exceptions;

public class CheckLimitActionException extends RuntimeException {

    public CheckLimitActionException(String error){
        super(error);
    }
    public CheckLimitActionException(String error, Exception exception){
        super(error, exception);
    }
    public CheckLimitActionException(Exception exception){
        super(exception.getMessage(), exception);
    }

}
