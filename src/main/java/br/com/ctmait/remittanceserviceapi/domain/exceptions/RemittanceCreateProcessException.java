package br.com.ctmait.remittanceserviceapi.domain.exceptions;

public class RemittanceCreateProcessException extends RuntimeException {

    public RemittanceCreateProcessException(String error){
        super(error);
    }
    public RemittanceCreateProcessException(String error, Exception exception){
        super(error, exception);
    }
    public RemittanceCreateProcessException(Exception exception){
        super(exception.getMessage(), exception);
    }

}
