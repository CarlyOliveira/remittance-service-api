package br.com.ctmait.remittanceserviceapi.tech.rest.resources;

import br.com.ctmait.remittanceserviceapi.UtilTest;
import br.com.ctmait.remittanceserviceapi.abstraction.actions.*;
import br.com.ctmait.remittanceserviceapi.abstraction.validations.RemittanceCreateValidation;
import br.com.ctmait.remittanceserviceapi.domain.exceptions.*;
import br.com.ctmait.remittanceserviceapi.domain.models.remittance.Remittance;
import br.com.ctmait.remittanceserviceapi.domain.process.RemittanceCreateProcessImpl;
import br.com.ctmait.remittanceserviceapi.domain.process.RemittanceQueryProcessImpl;
import br.com.ctmait.remittanceserviceapi.tech.rest.payload.in.RemittancePayloadIn;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class RemittanceResourcesTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    private static final String TRANSACTION_ID = "3e09b1d3-c592-4657-8252-462430574622";

    @MockBean
    private RemittanceCreateValidation remittanceCreateValidation;
    @MockBean
    private PayerEnrichmentAction payerEnrichmentAction;
    @MockBean
    private ReceiverEnrichmentAction receiverEnrichmentAction;
    @MockBean
    private CheckBalanceAction checkBalanceAction;
    @MockBean
    private CheckLimitAction checkLimitAction;
    @MockBean
    private GetExchangeRateAction getExchangeRateAction;
    @MockBean
    private ConvertRemittanceValueAction convertRemittanceValueAction;
    @MockBean
    private RemittanceEffectivationAction remittanceEffectivationAction;
    @MockBean
    private LimitReserveUndoAction limitReserveUndoAction;
    @MockBean
    private BalanceReserveUndoAction balanceReserveUndoAction;
    @MockBean
    private RemittanceQueryAction remittanceQueryAction;

    @InjectMocks
    RemittanceQueryProcessImpl remittanceQueryProcess;
    @InjectMocks
    RemittanceCreateProcessImpl remittanceCreateProcess;



    @Test
    void createRemittanceHttpStatusCode201() throws Exception {

        var remittancePayloadIn = UtilTest.generatePayerPayloadIn();
        doNothing().when(remittanceCreateValidation).execute(any());
        doNothing().when(payerEnrichmentAction).execute(any());
        doNothing().when(receiverEnrichmentAction).execute(any());
        doNothing().when(checkBalanceAction).execute(any());
        doNothing().when(checkLimitAction).execute(any());
        doNothing().when(getExchangeRateAction).execute(any());
        doNothing().when(convertRemittanceValueAction).execute(any());
        doNothing().when(balanceReserveUndoAction).execute(any());
        doNothing().when(limitReserveUndoAction).execute(any());
        doNothing().when(remittanceEffectivationAction).execute(any());
        mockMvc.perform(post("/api/v1/remittance")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(remittancePayloadIn))
                        .header("transactionId", TRANSACTION_ID))
                .andExpect(status().isCreated());
    }

    @Test
    void createRemittanceHttpStatusCode422PayerEnrichmentActionException() throws Exception {
        var remittancePayloadIn = UtilTest.generatePayerPayloadIn();
        doNothing().when(remittanceCreateValidation).execute(any());
        doThrow(PayerEnrichmentActionException.class).when(payerEnrichmentAction).execute(any());
        mockMvc.perform(post("/api/v1/remittance")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(remittancePayloadIn))
                        .header("transactionId", TRANSACTION_ID))
                .andExpect(status().isUnprocessableEntity());
    }
    @Test
    void createRemittanceHttpStatusCode422ReceiverEnrichmentActionException() throws Exception {
        var remittancePayloadIn = UtilTest.generatePayerPayloadIn();
        doNothing().when(remittanceCreateValidation).execute(any());
        doNothing().when(payerEnrichmentAction).execute(any());
        doThrow(ReceiverEnrichmentActionException.class).when(receiverEnrichmentAction).execute(any());
        mockMvc.perform(post("/api/v1/remittance")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(remittancePayloadIn))
                        .header("transactionId", TRANSACTION_ID))
                .andExpect(status().isUnprocessableEntity());
    }
    @Test
    void createRemittanceHttpStatusCode422CheckBalanceActionException() throws Exception {
        var remittancePayloadIn = UtilTest.generatePayerPayloadIn();
        doNothing().when(remittanceCreateValidation).execute(any());
        doNothing().when(payerEnrichmentAction).execute(any());
        doNothing().when(receiverEnrichmentAction).execute(any());
        doThrow(CheckBalanceActionException.class).when(checkBalanceAction).execute(any());
        mockMvc.perform(post("/api/v1/remittance")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(remittancePayloadIn))
                        .header("transactionId", TRANSACTION_ID))
                .andExpect(status().isUnprocessableEntity());
    }
    @Test
    void createRemittanceHttpStatusCode422CheckLimitActionException() throws Exception {
        var remittancePayloadIn = UtilTest.generatePayerPayloadIn();
        doNothing().when(remittanceCreateValidation).execute(any());
        doNothing().when(payerEnrichmentAction).execute(any());
        doNothing().when(receiverEnrichmentAction).execute(any());
        doNothing().when(checkBalanceAction).execute(any());
        doNothing().when(balanceReserveUndoAction).execute(any());
        doThrow(CheckLimitActionException.class).when(checkLimitAction).execute(any());
        mockMvc.perform(post("/api/v1/remittance")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(remittancePayloadIn))
                        .header("transactionId", TRANSACTION_ID))
                .andExpect(status().isUnprocessableEntity());
    }
    @Test
    void createRemittanceHttpStatusCode424GetExchangeRateActionException() throws Exception {
        var remittancePayloadIn = UtilTest.generatePayerPayloadIn();
        doNothing().when(remittanceCreateValidation).execute(any());
        doNothing().when(payerEnrichmentAction).execute(any());
        doNothing().when(receiverEnrichmentAction).execute(any());
        doNothing().when(checkBalanceAction).execute(any());
        doNothing().when(checkLimitAction).execute(any());
        doNothing().when(balanceReserveUndoAction).execute(any());
        doNothing().when(limitReserveUndoAction).execute(any());
        doThrow(GetExchangeRateActionException.class).when(getExchangeRateAction).execute(any());
        mockMvc.perform(post("/api/v1/remittance")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(remittancePayloadIn))
                        .header("transactionId", TRANSACTION_ID))
                .andExpect(status().isFailedDependency());
    }
    @Test
    void createRemittanceHttpStatusCode422ConvertRemittanceValueActionException() throws Exception {
        var remittancePayloadIn = UtilTest.generatePayerPayloadIn();
        doNothing().when(remittanceCreateValidation).execute(any());
        doNothing().when(payerEnrichmentAction).execute(any());
        doNothing().when(receiverEnrichmentAction).execute(any());
        doNothing().when(checkBalanceAction).execute(any());
        doNothing().when(checkLimitAction).execute(any());
        doNothing().when(getExchangeRateAction).execute(any());
        doNothing().when(balanceReserveUndoAction).execute(any());
        doNothing().when(limitReserveUndoAction).execute(any());
        doThrow(ConvertRemittanceValueActionException.class).when(convertRemittanceValueAction).execute(any());
        mockMvc.perform(post("/api/v1/remittance")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(remittancePayloadIn))
                        .header("transactionId", TRANSACTION_ID))
                .andExpect(status().isUnprocessableEntity());
    }@Test
    void createRemittanceHttpStatusCode422RemittanceEffectivationActionException() throws Exception {
        var remittancePayloadIn = UtilTest.generatePayerPayloadIn();
        doNothing().when(remittanceCreateValidation).execute(any());
        doNothing().when(payerEnrichmentAction).execute(any());
        doNothing().when(receiverEnrichmentAction).execute(any());
        doNothing().when(checkBalanceAction).execute(any());
        doNothing().when(checkLimitAction).execute(any());
        doNothing().when(getExchangeRateAction).execute(any());
        doNothing().when(convertRemittanceValueAction).execute(any());
        doNothing().when(balanceReserveUndoAction).execute(any());
        doNothing().when(limitReserveUndoAction).execute(any());
        doThrow(RemittanceEffectivationActionException.class).when(remittanceEffectivationAction).execute(any());
        mockMvc.perform(post("/api/v1/remittance")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(remittancePayloadIn))
                        .header("transactionId", TRANSACTION_ID))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void createRemittanceHttpStatusCode400() throws Exception {

        var remittancePayloadIn = new RemittancePayloadIn();
        mockMvc.perform(post("/api/v1/remittance")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(remittancePayloadIn)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getRemittanceHttpStatusCode200() throws Exception {

        doNothing().when(remittanceQueryAction).execute(any(Remittance.class));
        mockMvc.perform(get("/api/v1/remittance/3e09b1d3-c592-4657-8252-462430574644")
                        .header("transactionId", TRANSACTION_ID))
                .andExpect(status().isOk());
    }

    @Test
    void getRemittanceHttpStatusCode400() throws Exception {
        mockMvc.perform(get("/api/v1/remittance/3e09b1d3-c592-4657-8252-462430574622"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getRemittanceHttpStatusCode404() throws Exception {
        doThrow(RemittanceNotFoundException.class).when(remittanceQueryAction).execute(any());
        mockMvc.perform(get("/api/v1/remittance/3e09b1d3-c592-4657-8252-462430574622")
                .header("transactionId", TRANSACTION_ID))
                .andExpect(status().isNotFound());
        verify(remittanceQueryAction, times(1)).execute(any());
    }

    @Test
    void getRemittanceOnHttpStatusCode200() throws Exception {
        mockMvc.perform(get("/api/v1/remittance/status")).andExpect(status().isOk());
    }

}
