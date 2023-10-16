package br.com.ctmait.remittanceserviceapi.domain.exceptions;

public class ReceiverIsNotOwnerAccountException extends RuntimeException {

    public ReceiverIsNotOwnerAccountException(String error){
        super(error);
    }
}
