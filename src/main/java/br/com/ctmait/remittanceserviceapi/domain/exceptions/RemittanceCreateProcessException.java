package br.com.ctmait.remittanceserviceapi.domain.exceptions;

public class RemittanceCreateProcessException extends RuntimeException {

    public RemittanceCreateProcessException(Exception exception){
        super(exception.getMessage(), exception);
    }

}
