package br.com.ctmait.remittanceserviceapi.tech.rest.payload.in;

import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class RemittancePayloadIn {
    private BigDecimal value;
    private PersonPayloadIn payer;
    private PersonPayloadIn receiver;
}