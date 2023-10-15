package br.com.ctmait.remittanceserviceapi.domain.exceptions;

public class GetExchangeRateActionException extends RuntimeException {

    public GetExchangeRateActionException(Exception exception){
        super(exception.getMessage(), exception);
    }

}
