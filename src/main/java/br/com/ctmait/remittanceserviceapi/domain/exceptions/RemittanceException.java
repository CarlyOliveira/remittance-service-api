package br.com.ctmait.remittanceserviceapi.domain.exceptions;

public class RemittanceException extends RuntimeException {

    public RemittanceException(String error){
        super(error);
    }
    public RemittanceException(String error, Exception exception){
        super(error, exception);
    }
    public RemittanceException(Exception exception){
        super(exception.getMessage(), exception);
    }

}
