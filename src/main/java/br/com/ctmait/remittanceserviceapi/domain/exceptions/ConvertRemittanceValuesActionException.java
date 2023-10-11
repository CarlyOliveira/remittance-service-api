package br.com.ctmait.remittanceserviceapi.domain.exceptions;

public class ConvertRemittanceValuesActionException extends RuntimeException {

    public ConvertRemittanceValuesActionException(String error){
        super(error);
    }
    public ConvertRemittanceValuesActionException(String error, Exception exception){
        super(error, exception);
    }
    public ConvertRemittanceValuesActionException(Exception exception){
        super(exception.getMessage(), exception);
    }

}
