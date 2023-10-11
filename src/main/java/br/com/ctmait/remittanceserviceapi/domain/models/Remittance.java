package br.com.ctmait.remittanceserviceapi.domain.models;

import java.util.function.Consumer;

public class Remittance {

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void visit(Consumer<Remittance> visitor) {
        visitor.accept(this);
    }
}