package br.com.ctmait.remittanceserviceapi.domain.models.account;

import br.com.ctmait.remittanceserviceapi.domain.models.remittance.Remittance;

import java.math.RoundingMode;
import java.util.Arrays;

public enum Currency {
    REAL("REAL"){
        @Override
        public void convertRemittanceValue(Remittance remittance) {
            var convertedValue = remittance.getValue().divide(remittance.getExchangeRate(), 5, RoundingMode.HALF_DOWN);
            remittance.setConvertedValue(convertedValue);
        }
    },
    DOLAR("DOLAR"){
        @Override
        public void convertRemittanceValue(Remittance remittance) {
            var convertedValue = remittance.getValue().multiply(remittance.getExchangeRate()).setScale(5, RoundingMode.HALF_DOWN);
            remittance.setConvertedValue(convertedValue);
        }
    };

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

    public abstract void convertRemittanceValue(Remittance remittance);

    @Override
    public String toString() {
        return "Currency{" +
                "code='" + code + '\'' +
                '}';
    }
}
