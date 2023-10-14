package br.com.ctmait.remittanceserviceapi.tech.rest.payload.out;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ExchangeRateValue {
    private String cotacaoCompra;
    private String cotacaoVenda;
    private String dataHoraCotacao;
}
