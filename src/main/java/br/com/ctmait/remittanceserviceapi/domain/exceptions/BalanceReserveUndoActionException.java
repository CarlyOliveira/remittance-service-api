package br.com.ctmait.remittanceserviceapi.domain.exceptions;

public class BalanceReserveUndoActionException extends RuntimeException {

    public BalanceReserveUndoActionException(Exception exception){
        super(exception.getMessage(), exception);
    }

}
