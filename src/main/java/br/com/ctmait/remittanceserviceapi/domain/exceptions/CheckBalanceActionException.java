package br.com.ctmait.remittanceserviceapi.domain.exceptions;

public class CheckBalanceActionException extends RuntimeException {

    public CheckBalanceActionException(String error){
        super(error);
    }
    public CheckBalanceActionException(Exception exception){
        super(exception.getMessage(), exception);
    }

}
