package br.com.ctmait.remittanceserviceapi.tech.aws.dynamodb.repository;

import br.com.ctmait.remittanceserviceapi.domain.models.remittance.Payer;
import br.com.ctmait.remittanceserviceapi.domain.models.remittance.Receiver;
import br.com.ctmait.remittanceserviceapi.domain.models.remittance.Remittance;
import br.com.ctmait.remittanceserviceapi.domain.models.user.Document;
import br.com.ctmait.remittanceserviceapi.domain.models.user.DocumentType;
import br.com.ctmait.remittanceserviceapi.tech.aws.dynamodb.entity.RemittanceEntity;
import br.com.ctmait.remittanceserviceapi.tech.infrastructure.repository.RemittanceRepository;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTableMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;


@Repository
@RequiredArgsConstructor
public class RemittanceRepositoryDynamodb implements RemittanceRepository {

    private static final Logger log = LoggerFactory.getLogger(RemittanceRepositoryDynamodb.class);

    private final DynamoDBMapper dynamoDBMapper;

    @Override
    public Remittance getById(String remittanceId){
        log.info("RRD-GBI-00 get remittance by id {}", remittanceId);
        try {
            Objects.requireNonNull(remittanceId, "remittanceId cannot null");
            var remittanceEntity = this.get(remittanceId);
            var remittance = this.convert(remittanceEntity);
            log.info("RRD-GBI-01 get return remittance {}", remittance);
            return remittance;
        }catch (Exception exception){
            log.error("RRD-GBI-02 error {} get remittance by id {} ", exception, remittanceId);
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
        }catch (Exception exception){
            log.error("RRD-I-02 error {} creating remittance {} ", exception, remittance);
            throw exception;
        }
    }

    private RemittanceEntity get(String remittanceId){
        try {
            Objects.requireNonNull(remittanceId, "remittanceId cannot null");
            var remittanceEntity = dynamoDBMapper.load(RemittanceEntity.class, remittanceId);
            return remittanceEntity;
        }catch (Exception e){
            throw e;
        }
    }

    private Remittance convert (RemittanceEntity remittanceEntity){
        var remittance = new Remittance();
        remittance.setId(remittanceEntity.getId());
        remittance.setValue(new BigDecimal(remittanceEntity.getValue()));
        remittance.setExchangeRateDate(LocalDate.parse(remittanceEntity.getExchangeRateDate()));
        remittance.setConvertedValue(new BigDecimal(remittanceEntity.getConvertedValue()));
        remittance.setExchangeRate(new BigDecimal(remittanceEntity.getExchangeRate()));

        var payer = new Payer();
        payer.setUserName(remittanceEntity.getPayerName());
        payer.setAccountId(remittanceEntity.getPayerAccountId());

        var payerDocument= new Document();
        payerDocument.setValue(remittanceEntity.getPayerDocument());
        payerDocument.setDocumentType(DocumentType.getByCode(remittanceEntity.getPayerDocumentType()));
        payer.setDocument(payerDocument);
        remittance.setPayer(payer);

        var receiver = new Receiver();
        receiver.setUserName(remittanceEntity.getPayerName());
        receiver.setAccountId(remittanceEntity.getPayerAccountId());
        var receiverDocument= new Document();
        receiverDocument.setValue(remittanceEntity.getPayerDocument());
        receiverDocument.setDocumentType(DocumentType.getByCode(remittanceEntity.getPayerDocumentType()));
        receiver.setDocument(payerDocument);
        remittance.setReceiver(receiver);

        return remittance;
    }
    private RemittanceEntity convert (Remittance remittance){
        var remittanceEntity = new RemittanceEntity();

        remittanceEntity.setId(remittanceEntity.getId());
        remittanceEntity.setConvertedValue(remittance.getConvertedValue().toPlainString());
        remittanceEntity.setExchangeRate(remittance.getExchangeRate().toPlainString());
        remittanceEntity.setExchangeRateDate(remittanceEntity.getExchangeRateDate());
        remittanceEntity.setValue(remittance.getValue().toPlainString());

        remittanceEntity.setPayerAccountId(remittance.getPayer().getAccountId());
        remittanceEntity.setPayerDocument(remittance.getPayer().getDocument().getValue());
        remittanceEntity.setPayerName(remittance.getPayer().getUserName());
        remittanceEntity.setPayerDocumentType(remittance.getPayer().getDocument().getDocumentType().getCode());

        remittanceEntity.setReceiverAccountId(remittance.getReceiver().getAccountId());
        remittanceEntity.setReceiverDocument(remittance.getReceiver().getDocument().getValue());
        remittanceEntity.setReceiverName(remittance.getReceiver().getUserName());
        remittanceEntity.setReceiverDocumentType(remittance.getReceiver().getDocument().getDocumentType().getCode());

        return remittanceEntity;
    }
}
