package br.com.ctmait.remittanceserviceapi.domain.models.remittance;


import br.com.ctmait.remittanceserviceapi.domain.models.account.Balance;
import br.com.ctmait.remittanceserviceapi.domain.models.account.TransactionalLimit;
import br.com.ctmait.remittanceserviceapi.domain.models.user.Document;

public class Payer {
    private String userName;
    private String accountId;
    private Balance balance;
    private TransactionalLimit limit;
    private Document document;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public TransactionalLimit getLimit() {
        return limit;
    }

    public void setLimit(TransactionalLimit limit) {
        this.limit = limit;
    }

    public Balance getBalance() {
        return balance;
    }

    public void setBalance(Balance balance) {
        this.balance = balance;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    @Override
    public String toString() {
        return "Payer{" +
                ", userName='" + userName + '\'' +
                ", accountId='" + accountId + '\'' +
                ", balance=" + balance +
                ", limit=" + limit +
                ", document=" + document +
                '}';
    }
}