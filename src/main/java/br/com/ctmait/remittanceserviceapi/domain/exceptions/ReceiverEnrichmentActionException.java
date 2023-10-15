package br.com.ctmait.remittanceserviceapi.domain.exceptions;

public class ReceiverEnrichmentActionException extends RuntimeException {

    public ReceiverEnrichmentActionException(Exception exception){
        super(exception.getMessage(), exception);
    }

}
