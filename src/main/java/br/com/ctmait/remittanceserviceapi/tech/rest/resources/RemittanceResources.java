package br.com.ctmait.remittanceserviceapi.tech.rest.resources;

import br.com.ctmait.remittanceserviceapi.abstraction.process.RemittanceCreateProcess;
import br.com.ctmait.remittanceserviceapi.domain.models.remittance.Remittance;
import br.com.ctmait.remittanceserviceapi.tech.rest.mapper.RemittanceMapper;
import br.com.ctmait.remittanceserviceapi.tech.rest.payload.in.RemittancePayloadIn;
import br.com.ctmait.remittanceserviceapi.tech.rest.payload.out.RemittancePayloadOut;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RemittanceResources {

    private static final Logger log = LoggerFactory.getLogger(RemittanceResources.class);

    @Value("${rest.resources.remmitanceserviceapi.response.base-path-location}")
    private String RESPONSE_CREATE_BASE_PATH_URI;

    private final RemittanceCreateProcess remittanceCreateProcess;

    @GetMapping("/v1/remittance/status")
    public ResponseEntity<String> on() {
        log.info("RS-O-00 Remittance status");
        var remittance = new Remittance();
        remittance.visit(remittanceCreateProcess::execute);
        System.out.println(remittance);
        return ResponseEntity.ok("Remittance Service ON");
    }

    @PostMapping("/v1/remittance")
    public ResponseEntity<?> createRemittance(@RequestBody RemittancePayloadIn remittancePayloadIn,
                                              @RequestHeader(value = "transactionId", required = true) String transactionId){

        log.info("RR-CR-00 create remittance request {} transactionId {}", remittancePayloadIn, transactionId);

        var remittance = RemittanceMapper.INSTANCE.map(remittancePayloadIn,transactionId);

        log.info("AR-CA-01 create remittance {}", remittance);

        remittance.visit(remittanceCreateProcess::execute);

        log.info("AR-CA-02 created remittance {}", remittance);

        var response = ResponseEntity.created(URI.create(RESPONSE_CREATE_BASE_PATH_URI + "/" + remittance.getId())).build();

        log.info("AR-CA-03 create remittance response {}", response);

        return response;
    }

    @GetMapping("/v1/remittance/{id}")
    public ResponseEntity<RemittancePayloadOut> getRemittance(@PathVariable String id,
                                                              @RequestHeader(value = "transactionId", required = true) String transactionId) {

        //TODO Implementar

        return ResponseEntity.ok(new RemittancePayloadOut());
    }

}
