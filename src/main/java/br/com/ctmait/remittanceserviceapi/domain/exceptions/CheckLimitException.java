package br.com.ctmait.remittanceserviceapi.domain.exceptions;

public class CheckLimitException extends RuntimeException {

    public CheckLimitException(String error){
        super(error);
    }
    public CheckLimitException(String error, Exception exception){
        super(error, exception);
    }
    public CheckLimitException(Exception exception){
        super(exception.getMessage(), exception);
    }

}
