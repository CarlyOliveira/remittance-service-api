package br.com.ctmait.remittanceserviceapi.tech.aws.dynamodb.repository;

import br.com.ctmait.remittanceserviceapi.UtilTest;
import br.com.ctmait.remittanceserviceapi.domain.models.remittance.Remittance;
import br.com.ctmait.remittanceserviceapi.tech.aws.dynamodb.entity.RemittanceEntity;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class RemittanceRepositoryDynamodbTest {

    @Mock
    private DynamoDBMapper dynamoDBMapper;

    @InjectMocks
    private RemittanceRepositoryDynamodb remittanceRepositoryDynamodb;

    @Test
    void loadSuccess() {
        var remittanceEntity = UtilTest.generateRemittanceEntityFromRemittancePfRealToPjDolar();
        var remittance = new Remittance();
        remittance.setId(remittanceEntity.getId());
        when(dynamoDBMapper.load(RemittanceEntity.class, remittance.getId())).thenReturn(remittanceEntity);
        remittanceRepositoryDynamodb.load(remittance);

        assertNotNull(remittance);
        assertEquals(remittance.getValue().toPlainString(), remittanceEntity.getValue());
        assertEquals(remittance.getId(), remittanceEntity.getId());
        assertEquals(remittance.getRemittanceStatus().getCode(), remittanceEntity.getRemittanceStatus());
        assertEquals(remittance.getConvertedValue().toPlainString(), remittanceEntity.getConvertedValue());
        assertEquals(remittance.getRemittanceCreateDate().toString(), remittanceEntity.getRemittanceCreateDate());
        assertEquals(remittance.getExchangeRate().toPlainString(), remittanceEntity.getExchangeRate());
        assertEquals(remittance.getExchangeRateDate().toString(), remittanceEntity.getExchangeRateDate());

        assertNotNull(remittance.getPayer());
        assertEquals(remittance.getPayer().getAccountId(), remittanceEntity.getPayerAccountId());
        assertEquals(remittance.getPayer().getDocument().getValue(), remittanceEntity.getPayerDocument());
        assertEquals(remittance.getPayer().getDocument().getDocumentType().getCode(), remittanceEntity.getPayerDocumentType());
        assertEquals(remittance.getPayer().getUserName(), remittanceEntity.getPayerName());

        assertNotNull(remittance.getReceiver());
        assertEquals(remittance.getReceiver().getAccountId(), remittanceEntity.getReceiverAccountId());
        assertEquals(remittance.getReceiver().getDocument().getValue(), remittanceEntity.getReceiverDocument());
        assertEquals(remittance.getReceiver().getDocument().getDocumentType().getCode(), remittanceEntity.getReceiverDocumentType());
        assertEquals(remittance.getReceiver().getUserName(), remittanceEntity.getReceiverName());

    }

}
