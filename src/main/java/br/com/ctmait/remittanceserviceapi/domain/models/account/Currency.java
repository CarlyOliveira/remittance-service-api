package br.com.ctmait.remittanceserviceapi.domain.models.account;

import java.util.Arrays;

public enum Currency {
    REAL("REAL"),
    DOLAR("DOLAR");

    private String code;

    Currency(String code) {
        this.code = code;
    }

    public static Currency getByCode(String code){
        return Arrays.stream(values())
                .filter(state -> state.code.equalsIgnoreCase(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("code for Currency not found"));
    }

    public String getCode() {
        return code;
    }

    @Override
    public String toString() {
        return "Currency{" +
                "code='" + code + '\'' +
                '}';
    }
}
