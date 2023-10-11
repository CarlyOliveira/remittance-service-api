package br.com.ctmait.remittanceserviceapi.domain.exceptions;

public class GetExchangeRateActionException extends RuntimeException {

    public GetExchangeRateActionException(String error){
        super(error);
    }
    public GetExchangeRateActionException(String error, Exception exception){
        super(error, exception);
    }
    public GetExchangeRateActionException(Exception exception){
        super(exception.getMessage(), exception);
    }

}
