package br.com.ctmait.remittanceserviceapi.domain.models.user;

import java.util.Arrays;

public enum DocumentType {
    CPF("CPF"),
    CNPJ("CNPJ");

    private String code;

    DocumentType(String code) {
        this.code = code;
    }

    public static DocumentType getByCode(String code){
        return Arrays.stream(values())
                .filter(state -> state.code.equalsIgnoreCase(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("code for DocumentType not found"));
    }

    public String getCode() {
        return code;
    }

    @Override
    public String toString() {
        return "DocumentType{" +
                "code='" + code + '\'' +
                '}';
    }
}
