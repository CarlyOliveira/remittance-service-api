package br.com.ctmait.remittanceserviceapi.domain.exceptions;

public class PayerEnrichmentActionException extends RuntimeException {

    public PayerEnrichmentActionException(Exception exception){
        super(exception.getMessage(), exception);
    }

}
