package br.com.ctmait.remittanceserviceapi.domain.models.remittance;


import br.com.ctmait.remittanceserviceapi.domain.models.user.Document;

public class Receiver {
    private String userId;
    private String userName;
    private String accountId;
    private Document document;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

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

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    @Override
    public String toString() {
        return "Receiver{" +
                "userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", accountId='" + accountId + '\'' +
                ", document=" + document +
                '}';
    }
}