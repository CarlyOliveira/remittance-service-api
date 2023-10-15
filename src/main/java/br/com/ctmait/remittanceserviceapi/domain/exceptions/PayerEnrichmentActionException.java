package br.com.ctmait.remittanceserviceapi.domain.exceptions;

public class PayerEnrichmentActionException extends RuntimeException {

    public PayerEnrichmentActionException(String error){
        super(error);
    }
    public PayerEnrichmentActionException(String error, Exception exception){
        super(error, exception);
    }
    public PayerEnrichmentActionException(Exception exception){
        super(exception.getMessage(), exception);
    }

}
