package br.com.ctmait.remittanceserviceapi.domain.models.remittance;

import java.util.Arrays;

public enum RemittanceStatus {
    EFETIVADO("EFETIVADO"),
    INCLUIDO("INCLUIDO"),
    CANCELADO("CANCELADO");

    private String code;

    RemittanceStatus(String code) {
        this.code = code;
    }

    public static RemittanceStatus getByCode(String code){
        return Arrays.stream(values())
                .filter(state -> state.code.equalsIgnoreCase(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("code for RemittanceStatus not found"));
    }

    public String getCode() {
        return code;
    }

    @Override
    public String toString() {
        return "RemittanceStatus{" +
                "code='" + code + '\'' +
                '}';
    }
}
