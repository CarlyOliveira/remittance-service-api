package br.com.ctmait.remittanceserviceapi.domain.models.remittance;

import java.math.BigDecimal;
import java.util.function.Consumer;

public class Remittance {

    private String id;
    private BigDecimal value;
    private BigDecimal convertedValue;
    private Payer payer;
    private Payer receiver;

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

    public BigDecimal getConvertedValue() {
        return convertedValue;
    }

    public void setConvertedValue(BigDecimal convertedValue) {
        this.convertedValue = convertedValue;
    }

    public Payer getPayer() {
        return payer;
    }

    public void setPayer(Payer payer) {
        this.payer = payer;
    }

    public Payer getReceiver() {
        return receiver;
    }

    public void setReceiver(Payer receiver) {
        this.receiver = receiver;
    }

    public void visit(Consumer<Remittance> visitor) {
        visitor.accept(this);
    }

    @Override
    public String toString() {
        return "Remittance{" +
                "id='" + id + '\'' +
                ", value=" + value +
                ", convertedValue=" + convertedValue +
                ", payer=" + payer +
                ", receiver=" + receiver +
                '}';
    }
}