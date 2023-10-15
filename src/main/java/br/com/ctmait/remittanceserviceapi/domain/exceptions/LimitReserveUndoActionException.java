package br.com.ctmait.remittanceserviceapi.domain.exceptions;

public class LimitReserveUndoActionException extends RuntimeException {

    public LimitReserveUndoActionException(String error){
        super(error);
    }
    public LimitReserveUndoActionException(String error, Exception exception){
        super(error, exception);
    }
    public LimitReserveUndoActionException(Exception exception){
        super(exception.getMessage(), exception);
    }

}
