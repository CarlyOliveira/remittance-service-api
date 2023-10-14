package br.com.ctmait.remittanceserviceapi.tech.rest.resources;

import br.com.ctmait.remittanceserviceapi.abstraction.actions.GetExchangeRateAction;
import br.com.ctmait.remittanceserviceapi.domain.models.remittance.Remittance;
import br.com.ctmait.remittanceserviceapi.tech.rest.client.ExchangeRateClient;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RemittanceResources {

    private static final Logger log = LoggerFactory.getLogger(RemittanceResources.class);
    private final GetExchangeRateAction getExchangeRateAction;

    @GetMapping("/v1/remittance/status")
    public ResponseEntity<String> on() {
        log.info("RS-O-00 Remittance status");
        var remittance = new Remittance();
        remittance.visit(getExchangeRateAction::execute);
        System.out.println(remittance);
        return ResponseEntity.ok("Remittance Service ON");
    }

}
