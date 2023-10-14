package br.com.ctmait.remittanceserviceapi.tech.rest.config;

import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExchangeRateClientConfig {

    @Bean
    public ErrorDecoder errorDecoder(){
        return new ExchangeRateClientDecoder();
    }
}
