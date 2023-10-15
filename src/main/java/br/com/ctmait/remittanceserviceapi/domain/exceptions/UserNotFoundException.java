package br.com.ctmait.remittanceserviceapi.domain.exceptions;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String error){
        super(error);
    }
}
