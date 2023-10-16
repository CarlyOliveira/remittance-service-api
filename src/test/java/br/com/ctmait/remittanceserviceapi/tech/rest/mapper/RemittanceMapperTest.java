package br.com.ctmait.remittanceserviceapi.tech.rest.mapper;

import br.com.ctmait.remittanceserviceapi.UtilTest;
import br.com.ctmait.remittanceserviceapi.domain.validations.RemittanceCreateValidationImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class RemittanceMapperTest {

    @InjectMocks
    private RemittanceCreateValidationImpl remittanceCreateValidation;

    @Test
    void mapRemittancesuccess() {
        var remittancePayloadIn = UtilTest.generateRemittancePayloadIn();
        var transactionId = "3e09b1d3-c592-4657-8252-462430574644";
        var remittance = RemittanceMapper.INSTANCE.map(remittancePayloadIn,transactionId);
        assertEquals(remittance.getId(), transactionId);
        assertEquals(remittance.getValue(), remittancePayloadIn.getValue());
        assertNotNull(remittance.getPayer());
        assertEquals(remittance.getPayer().getAccountId(), remittancePayloadIn.getPayer().getAccountId());
        assertNotNull(remittance.getPayer().getDocument());
        assertEquals(remittance.getPayer().getDocument().getValue(), remittancePayloadIn.getPayer().getDocument().getValue());
        assertEquals(remittance.getPayer().getDocument().getDocumentType(), remittancePayloadIn.getPayer().getDocument().getDocumentType());
        assertNotNull(remittance.getReceiver());
        assertEquals(remittance.getReceiver().getAccountId(), remittancePayloadIn.getReceiver().getAccountId());
        assertNotNull(remittance.getReceiver().getDocument());
        assertEquals(remittance.getReceiver().getDocument().getValue(), remittancePayloadIn.getReceiver().getDocument().getValue());
        assertEquals(remittance.getReceiver().getDocument().getDocumentType(), remittancePayloadIn.getReceiver().getDocument().getDocumentType());
    }

    @Test
    void mapRemittancePayloadOutsuccess() {
        var remittance = UtilTest.generateRemittancePfRealToPjDolar();
        var remittancePayloadOut = RemittanceMapper.INSTANCE.map(remittance);

        assertEquals(remittancePayloadOut.id(), remittance.getId());
        assertEquals(remittancePayloadOut.value(), remittance.getValue().toPlainString());
        assertEquals(remittancePayloadOut.valueCurrency(), remittance.getPayer().getBalance().getCurrency().getCode());
        assertEquals(remittancePayloadOut.convertedValue(), remittance.getConvertedValue().toPlainString());
        assertEquals(remittancePayloadOut.convertedValueCurrency(), remittance.getReceiver().getAccountCurrency().getCode());
        assertEquals(remittancePayloadOut.exchangeRate(), remittance.getExchangeRate().toPlainString());
        assertEquals(remittancePayloadOut.exchangeRateDate(), remittance.getExchangeRateDate().toString());
        assertEquals(remittancePayloadOut.exchangeRateDate(), remittance.getExchangeRateDate().toString());

        assertEquals(remittancePayloadOut.payerName(), remittance.getPayer().getUserName());
        assertEquals(remittancePayloadOut.payerAccountId(), remittance.getPayer().getAccountId());
        assertEquals(remittancePayloadOut.payerDocument(), remittance.getPayer().getDocument().getValue());
        assertEquals(remittancePayloadOut.payerDocumentType(), remittance.getPayer().getDocument().getDocumentType().getCode());

        assertEquals(remittancePayloadOut.receiverName(), remittance.getReceiver().getUserName());
        assertEquals(remittancePayloadOut.receiverAccountId(), remittance.getReceiver().getAccountId());
        assertEquals(remittancePayloadOut.receiverDocument(), remittance.getReceiver().getDocument().getValue());
        assertEquals(remittancePayloadOut.receiverDocumentType(), remittance.getReceiver().getDocument().getDocumentType().getCode());

        assertEquals(remittancePayloadOut.remittanceCreateDate(), DateTimeFormatter.ISO_DATE_TIME.format(remittance.getRemittanceCreateDate()));
        assertEquals(remittancePayloadOut.remittanceStatus(), remittance.getRemittanceStatus().getCode());

    }
}