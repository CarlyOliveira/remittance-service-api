package br.com.ctmait.remittanceserviceapi.tech.rest.payload.out;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ExchangeRatePayloadOut {
    @JsonAlias("@odata.context")
    private String dataContex;
    @JsonAlias("value")
    private List<ExchangeRateValue> values;
}
