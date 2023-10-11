package br.com.ctmait.remittanceserviceapi.domain.exceptions;

public class RemittanceQueryProcessException extends RuntimeException {

    public RemittanceQueryProcessException(String error){
        super(error);
    }
    public RemittanceQueryProcessException(String error, Exception exception){
        super(error, exception);
    }
    public RemittanceQueryProcessException(Exception exception){
        super(exception.getMessage(), exception);
    }

}
