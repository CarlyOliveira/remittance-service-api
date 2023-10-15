package br.com.ctmait.remittanceserviceapi.tech.aws.dynamodb.repository;

import br.com.ctmait.remittanceserviceapi.domain.exceptions.RemittanceAlreadyExistsException;
import br.com.ctmait.remittanceserviceapi.domain.exceptions.RemittanceNotFoundException;
import br.com.ctmait.remittanceserviceapi.domain.models.account.Balance;
import br.com.ctmait.remittanceserviceapi.domain.models.account.Currency;
import br.com.ctmait.remittanceserviceapi.domain.models.remittance.Payer;
import br.com.ctmait.remittanceserviceapi.domain.models.remittance.Receiver;
import br.com.ctmait.remittanceserviceapi.domain.models.remittance.Remittance;
import br.com.ctmait.remittanceserviceapi.domain.models.remittance.RemittanceStatus;
import br.com.ctmait.remittanceserviceapi.domain.models.user.Document;
import br.com.ctmait.remittanceserviceapi.domain.models.user.DocumentType;
import br.com.ctmait.remittanceserviceapi.tech.aws.dynamodb.entity.RemittanceEntity;
import br.com.ctmait.remittanceserviceapi.tech.infrastructure.repository.RemittanceRepository;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTableMapper;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.Optional;


@Repository
@RequiredArgsConstructor
public class RemittanceRepositoryDynamodb implements RemittanceRepository {

    private static final Logger log = LoggerFactory.getLogger(RemittanceRepositoryDynamodb.class);

    private final DynamoDBMapper dynamoDBMapper;

    @Override
    public void load(Remittance remittance){
        log.info("RRD-L-00 load remittance {}", remittance);
        try {
            Objects.requireNonNull(remittance, "remittance cannot null");
            Objects.requireNonNull(remittance.getId(), "remittance Id cannot null");
            var remittanceEntity = this.get(remittance.getId());
            this.loadRemittance(remittance, remittanceEntity);
            log.info("RRD-L-01 loaded remittance {}", remittance);
        }catch (Exception exception){
            log.error("RRD-L-02 error {} load remittance {} ", exception, remittance);
            throw exception;
        }
    }

    @Override
    public void insert(Remittance remittance){
        log.info("RRD-I-00 create remittance {} ", remittance);
        try {
            Objects.requireNonNull(remittance, "remittance cannot null");
            var remittanceEntity = this.convert(remittance);
            DynamoDBTableMapper<RemittanceEntity, String, ?> dynamoDBTableMapper = dynamoDBMapper.newTableMapper(RemittanceEntity.class);
            dynamoDBTableMapper.saveIfNotExists(remittanceEntity);
            log.info("RRD-I-01 created remittance {} ", remittanceEntity);
        }catch (ConditionalCheckFailedException exception){
            log.error("RRD-I-02 error {} already exists remittance {} ", exception, remittance);
            throw new RemittanceAlreadyExistsException("Remittance already exists " + remittance.getId());
        }catch (Exception exception){
            log.error("RRD-I-03 error {} creating remittance {} ", exception, remittance);
            throw exception;
        }
    }

    private RemittanceEntity get(String remittanceId){
        try {
            Objects.requireNonNull(remittanceId, "remittanceId cannot null");
            var remittanceEntity = dynamoDBMapper.load(RemittanceEntity.class, remittanceId);

            return Optional.ofNullable(remittanceEntity)
                    .orElseThrow(() -> new RemittanceNotFoundException("remittance id " + remittanceId + " not found"));
        }catch (Exception e){
            throw e;
        }
    }

    private void loadRemittance(Remittance remittance, RemittanceEntity remittanceEntity){
        remittance.setValue(new BigDecimal(remittanceEntity.getValue()));
        remittance.setExchangeRateDate(LocalDate.parse(remittanceEntity.getExchangeRateDate()));
        remittance.setConvertedValue(new BigDecimal(remittanceEntity.getConvertedValue()));
        remittance.setExchangeRate(new BigDecimal(remittanceEntity.getExchangeRate()));

        var payer = new Payer();
        payer.setUserName(remittanceEntity.getPayerName());
        payer.setAccountId(remittanceEntity.getPayerAccountId());
        var payerBalance = new Balance();
        payerBalance.setCurrency(Currency.getByCode(remittanceEntity.getValueCurrency()));
        payer.setBalance(payerBalance);

        var payerDocument= new Document();
        payerDocument.setValue(remittanceEntity.getPayerDocument());
        payerDocument.setDocumentType(DocumentType.getByCode(remittanceEntity.getPayerDocumentType()));
        payer.setDocument(payerDocument);
        remittance.setPayer(payer);

        var receiver = new Receiver();
        receiver.setUserName(remittanceEntity.getReceiverName());
        receiver.setAccountId(remittanceEntity.getReceiverAccountId());
        receiver.setAccountCurrency(Currency.getByCode(remittanceEntity.getConvertedValueCurrency()));
        var receiverDocument= new Document();
        receiverDocument.setValue(remittanceEntity.getReceiverDocument());
        receiverDocument.setDocumentType(DocumentType.getByCode(remittanceEntity.getReceiverDocumentType()));
        receiver.setDocument(receiverDocument);
        remittance.setReceiver(receiver);

        remittance.setRemittanceStatus(RemittanceStatus.getByCode(remittanceEntity.getRemittanceStatus()));
        remittance.setRemittanceCreateDate(ZonedDateTime.parse(remittanceEntity.getRemittanceCreateDate()));
    }
    private RemittanceEntity convert (Remittance remittance){
        var remittanceEntity = new RemittanceEntity();

        remittanceEntity.setId(remittance.getId());
        remittanceEntity.setConvertedValue(remittance.getConvertedValue().toPlainString());
        remittanceEntity.setExchangeRate(remittance.getExchangeRate().toPlainString());
        remittanceEntity.setExchangeRateDate(remittance.getExchangeRateDate().toString());
        remittanceEntity.setValue(remittance.getValue().toPlainString());

        remittanceEntity.setPayerAccountId(remittance.getPayer().getAccountId());
        remittanceEntity.setPayerDocument(remittance.getPayer().getDocument().getValue());
        remittanceEntity.setPayerName(remittance.getPayer().getUserName());
        remittanceEntity.setPayerDocumentType(remittance.getPayer().getDocument().getDocumentType().getCode());

        remittanceEntity.setReceiverAccountId(remittance.getReceiver().getAccountId());
        remittanceEntity.setReceiverDocument(remittance.getReceiver().getDocument().getValue());
        remittanceEntity.setReceiverName(remittance.getReceiver().getUserName());
        remittanceEntity.setReceiverDocumentType(remittance.getReceiver().getDocument().getDocumentType().getCode());

        remittanceEntity.setValueCurrency(remittance.getPayer().getBalance().getCurrency().getCode());
        remittanceEntity.setConvertedValueCurrency(remittance.getReceiver().getAccountCurrency().getCode());

        remittanceEntity.setRemittanceCreateDate(ZonedDateTime.now().toString());
        remittanceEntity.setRemittanceStatus(RemittanceStatus.EFETIVADO.getCode());

        return remittanceEntity;
    }
}
