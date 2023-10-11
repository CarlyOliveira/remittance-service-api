package br.com.ctmait.remittanceserviceapi.domain.models.account;

import java.math.BigDecimal;

public class Balance {

    private BigDecimal value;
    private Currency currency;

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    @Override
    public String toString() {
        return "Balance{" +
                "value=" + value +
                ", currency=" + currency +
                '}';
    }
}