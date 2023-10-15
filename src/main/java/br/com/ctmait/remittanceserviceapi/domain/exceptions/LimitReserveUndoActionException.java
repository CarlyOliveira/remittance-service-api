package br.com.ctmait.remittanceserviceapi.domain.exceptions;

public class LimitReserveUndoActionException extends RuntimeException {

    public LimitReserveUndoActionException(Exception exception){
        super(exception.getMessage(), exception);
    }

}
