package br.com.ctmait.remittanceserviceapi.domain.exceptions;

public class RemittanceCreateValidationException extends RuntimeException {

    public RemittanceCreateValidationException(String error){
        super(error);
    }
    public RemittanceCreateValidationException(String error, Exception exception){
        super(error, exception);
    }
    public RemittanceCreateValidationException(Exception exception){
        super(exception.getMessage(), exception);
    }

}
