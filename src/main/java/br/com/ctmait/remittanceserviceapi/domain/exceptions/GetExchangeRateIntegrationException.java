package br.com.ctmait.remittanceserviceapi.domain.exceptions;

public class GetExchangeRateIntegrationException extends RuntimeException {

    public GetExchangeRateIntegrationException(String error){
        super(error);
    }
}
