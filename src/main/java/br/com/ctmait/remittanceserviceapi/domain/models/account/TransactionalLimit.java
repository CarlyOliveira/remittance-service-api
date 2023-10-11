package br.com.ctmait.remittanceserviceapi.domain.models.account;

import java.math.BigDecimal;

public class TransactionalLimit {

    private BigDecimal value;
    private final Currency currency = Currency.REAL;

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public Currency getCurrency() {
        return currency;
    }

    @Override
    public String toString() {
        return "TransactionalLimit{" +
                "value=" + value +
                ", currency=" + currency +
                '}';
    }
}