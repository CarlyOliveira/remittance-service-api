package br.com.ctmait.remittanceserviceapi.domain.models.remittance;


import br.com.ctmait.remittanceserviceapi.domain.models.account.Currency;
import br.com.ctmait.remittanceserviceapi.domain.models.user.Document;

public class Receiver {
    private String userName;
    private String accountId;
    private Currency accountCurrency;
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

    public Currency getAccountCurrency() {
        return accountCurrency;
    }

    public void setAccountCurrency(Currency accountCurrency) {
        this.accountCurrency = accountCurrency;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    @Override
    public String toString() {
        return "Receiver{" +
                "userName='" + userName + '\'' +
                ", accountId='" + accountId + '\'' +
                ", accountCurrency=" + accountCurrency +
                ", document=" + document +
                '}';
    }
}