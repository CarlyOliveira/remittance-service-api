package br.com.ctmait.remittanceserviceapi.domain.exceptions;

public class ReceiverEnrichmentActionException extends RuntimeException {

    public ReceiverEnrichmentActionException(String error){
        super(error);
    }
    public ReceiverEnrichmentActionException(String error, Exception exception){
        super(error, exception);
    }
    public ReceiverEnrichmentActionException(Exception exception){
        super(exception.getMessage(), exception);
    }

}
