package br.com.ctmait.remittanceserviceapi.tech.rest.client;


import br.com.ctmait.remittanceserviceapi.domain.exceptions.GetExchangeRateIntegrationException;
import br.com.ctmait.remittanceserviceapi.domain.models.remittance.Remittance;
import br.com.ctmait.remittanceserviceapi.tech.rest.config.ExchangeRateClientConfig;
import br.com.ctmait.remittanceserviceapi.tech.rest.payload.out.ExchangeRatePayloadOut;
import br.com.ctmait.remittanceserviceapi.tech.rest.payload.out.ExchangeRateValue;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Optional;

@FeignClient(url = "${rest.client.exchangerate.url}", name = "exchangeRateClient", configuration = ExchangeRateClientConfig.class)
public interface ExchangeRateClient {

    @GetMapping(value = "${rest.client.exchangerate.service}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ExchangeRatePayloadOut> getExchangeRateDolar(@RequestParam("top") int top,
                                                       @RequestParam("$skip") int skip,
                                                       @RequestParam("@dataCotacao") String exchangeRateDate);


    default ResponseEntity<ExchangeRatePayloadOut> getExchangeRateDolar(LocalDate exchangeRateDate) {
        var top = 1;
        var skip = 0;
        var dataCotacao = exchangeRateDate.format(DateTimeFormatter.ofPattern("MM-dd-yyyy"));
        return getExchangeRateDolar(top,skip,"'" + dataCotacao + "'");
    }

    default void loadExchangeRateOnRemittance(Remittance remittance) {
        var top = 1;
        var skip = 0;
        var dataCotacao = remittance.getExchangeRateDate().format(DateTimeFormatter.ofPattern("MM-dd-yyyy"));

        var response = getExchangeRateDolar(top,skip,"'" + dataCotacao + "'");
        var exchangeRate = Optional.ofNullable(response).map(ResponseEntity::getBody)
                .map(ExchangeRatePayloadOut::getValues)
                .orElse(Collections.emptyList())
                .stream()
                .findFirst()
                .map(ExchangeRateValue::getCotacaoCompra)
                .orElseThrow(() -> new GetExchangeRateIntegrationException("Error on integration : No has value on response: " + response));
        remittance.setExchangeRate(new BigDecimal(exchangeRate));
    }
}