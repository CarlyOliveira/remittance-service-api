package br.com.ctmait.remittanceserviceapi.domain.exceptions;

public class RemittanceQueryProcessException extends RuntimeException {

    public RemittanceQueryProcessException(Exception exception){
        super(exception.getMessage(), exception);
    }

}
