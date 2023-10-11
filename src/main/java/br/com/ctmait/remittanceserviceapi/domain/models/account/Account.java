package br.com.ctmait.remittanceserviceapi.domain.models.account;

public class Account {

    private String id;
    private String ownerId;
    private Balance balance;
    private TransactionalLimit transactionalLimitDaily;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public Balance getBalance() {
        return balance;
    }

    public void setBalance(Balance balance) {
        this.balance = balance;
    }

    public TransactionalLimit getTransactionalLimitDaily() {
        return transactionalLimitDaily;
    }

    public void setTransactionalLimitDaily(TransactionalLimit transactionalLimitDaily) {
        this.transactionalLimitDaily = transactionalLimitDaily;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id='" + id + '\'' +
                ", ownerId='" + ownerId + '\'' +
                ", balance=" + balance +
                ", transactionalLimitDaily=" + transactionalLimitDaily +
                '}';
    }
}