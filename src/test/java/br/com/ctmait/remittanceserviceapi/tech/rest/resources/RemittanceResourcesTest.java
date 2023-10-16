package br.com.ctmait.remittanceserviceapi.tech.rest.resources;

import br.com.ctmait.remittanceserviceapi.UtilTest;
import br.com.ctmait.remittanceserviceapi.domain.models.remittance.Remittance;
import br.com.ctmait.remittanceserviceapi.domain.process.RemittanceCreateProcessImpl;
import br.com.ctmait.remittanceserviceapi.domain.process.RemittanceQueryProcessImpl;
import br.com.ctmait.remittanceserviceapi.tech.rest.payload.in.RemittancePayloadIn;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
public class RemittanceResourcesTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    RemittanceCreateProcessImpl remittanceCreateProcess;
    @MockBean
    RemittanceQueryProcessImpl remittanceQueryProcess;


    @Test
    void createRemittanceHttpStatusCode201() throws Exception {

        var remittancePayloadIn = UtilTest.generatePayerPayloadIn();
        doNothing().when(remittanceCreateProcess).execute(any(Remittance.class));

        mockMvc.perform(post("/api/v1/remittance")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(remittancePayloadIn))
                        .header("transactionId", "3e09b1d3-c592-4657-8252-462430574677"))
                .andExpect(status().isCreated());
    }

    @Test
    void createRemittanceHttpStatusCode400() throws Exception {

        var remittancePayloadIn = new RemittancePayloadIn();
        doNothing().when(remittanceCreateProcess).execute(any(Remittance.class));

        mockMvc.perform(post("/api/v1/remittance")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(remittancePayloadIn)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getRemittanceHttpStatusCode200() throws Exception {

        doNothing().when(remittanceQueryProcess).execute(any(Remittance.class));
        mockMvc.perform(get("/api/v1/remittance/3e09b1d3-c592-4657-8252-462430574644")
                        .header("transactionId", "3e09b1d3-c592-4657-8252-462430574643"))
                .andExpect(status().isOk());
    }

    @Test
    void getRemittanceHttpStatusCode400() throws Exception {

        doNothing().when(remittanceQueryProcess).execute(any(Remittance.class));
        mockMvc.perform(get("/api/v1/remittance/3e09b1d3-c592-4657-8252-462430574622"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getRemittanceOnHttpStatusCode200() throws Exception {
        mockMvc.perform(get("/api/v1/remittance/status")).andExpect(status().isOk());
    }

}
