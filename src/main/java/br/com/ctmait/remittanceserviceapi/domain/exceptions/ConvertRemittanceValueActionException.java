package br.com.ctmait.remittanceserviceapi.domain.exceptions;

public class ConvertRemittanceValueActionException extends RuntimeException {

    public ConvertRemittanceValueActionException(String error){
        super(error);
    }
    public ConvertRemittanceValueActionException(String error, Exception exception){
        super(error, exception);
    }
    public ConvertRemittanceValueActionException(Exception exception){
        super(exception.getMessage(), exception);
    }

}
