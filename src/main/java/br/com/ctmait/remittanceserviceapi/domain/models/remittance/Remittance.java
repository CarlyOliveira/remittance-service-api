package br.com.ctmait.remittanceserviceapi.domain.models.remittance;

import java.math.BigDecimal;
import java.util.function.Consumer;

public class Remittance {

    private String id;
    private BigDecimal value;
    private Person payer;
    private Person receiver;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public Person getPayer() {
        return payer;
    }

    public void setPayer(Person payer) {
        this.payer = payer;
    }

    public Person getReceiver() {
        return receiver;
    }

    public void setReceiver(Person receiver) {
        this.receiver = receiver;
    }

    public void visit(Consumer<Remittance> visitor) {
        visitor.accept(this);
    }
}