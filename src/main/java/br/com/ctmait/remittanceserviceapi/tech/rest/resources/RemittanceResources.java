package br.com.ctmait.remittanceserviceapi.tech.rest.resources;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class RemittanceResources {

    private static final Logger log = LoggerFactory.getLogger(RemittanceResources.class);

    @GetMapping("/v1/remittance/status")
    public ResponseEntity<String> on() {
        log.info("RS-O-00 Remittance status");
        return ResponseEntity.ok("Remittance Service ON");
    }

}
