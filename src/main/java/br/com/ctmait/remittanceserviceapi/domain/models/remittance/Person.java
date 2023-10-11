package br.com.ctmait.remittanceserviceapi.domain.models.remittance;


import br.com.ctmait.remittanceserviceapi.domain.models.account.Balance;
import br.com.ctmait.remittanceserviceapi.domain.models.user.Document;

public class Person {
    private String userId;
    private String userName;
    private String accountId;
    private Balance balance;
    private Document document;
}