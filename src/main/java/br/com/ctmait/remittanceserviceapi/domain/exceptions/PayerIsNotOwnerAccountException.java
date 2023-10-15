package br.com.ctmait.remittanceserviceapi.domain.exceptions;

public class PayerIsNotOwnerAccountException extends RuntimeException {

    public PayerIsNotOwnerAccountException(String error){
        super(error);
    }
}
