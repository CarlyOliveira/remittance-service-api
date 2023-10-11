package br.com.ctmait.remittanceserviceapi.domain.exceptions;

public class CheckBalanceException extends RuntimeException {

    public CheckBalanceException(String error){
        super(error);
    }
    public CheckBalanceException(String error, Exception exception){
        super(error, exception);
    }
    public CheckBalanceException(Exception exception){
        super(exception.getMessage(), exception);
    }

}
