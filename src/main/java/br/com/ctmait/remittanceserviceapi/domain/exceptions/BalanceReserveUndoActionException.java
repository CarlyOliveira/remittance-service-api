package br.com.ctmait.remittanceserviceapi.domain.exceptions;

public class BalanceReserveUndoActionException extends RuntimeException {

    public BalanceReserveUndoActionException(String error){
        super(error);
    }
    public BalanceReserveUndoActionException(String error, Exception exception){
        super(error, exception);
    }
    public BalanceReserveUndoActionException(Exception exception){
        super(exception.getMessage(), exception);
    }

}
