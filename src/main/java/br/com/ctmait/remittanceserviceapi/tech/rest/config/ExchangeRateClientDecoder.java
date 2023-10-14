package br.com.ctmait.remittanceserviceapi.tech.rest.config;

import br.com.ctmait.remittanceserviceapi.domain.exceptions.GetExchangeRateIntegrationException;
import feign.Response;
import feign.codec.ErrorDecoder;


public class ExchangeRateClientDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String s, Response response) {
        return new GetExchangeRateIntegrationException("StatusCode " + response.status() + " " + response.reason());
    }
}
