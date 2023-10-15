package br.com.ctmait.remittanceserviceapi.tech.rest.mapper;

import br.com.ctmait.remittanceserviceapi.domain.models.remittance.Remittance;
import br.com.ctmait.remittanceserviceapi.tech.rest.payload.in.RemittancePayloadIn;
import br.com.ctmait.remittanceserviceapi.tech.rest.payload.out.RemittancePayloadOut;
import br.com.ctmait.remittanceserviceapi.tech.rest.payload.out.RemittancePayloadOutR;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RemittanceMapper {

    RemittanceMapper INSTANCE = Mappers.getMapper( RemittanceMapper.class );

    @Mapping(source = "source.receiver", target = "receiver")
    @Mapping(source = "source.payer", target = "payer")
    @Mapping(source = "source.value", target = "value")
    @Mapping(source = "transactionId", target = "id")
    Remittance map (RemittancePayloadIn source, String transactionId);

    @Mapping(source = "source.id", target = "id")
    @Mapping(source = "source.value", target = "value")
    @Mapping(source = "source.convertedValue", target = "convertedValue")
    @Mapping(source = "source.exchangeRate", target = "exchangeRate")
    @Mapping(source = "source.exchangeRateDate", target = "exchangeRateDate")
    @Mapping(source = "source.payer.userName", target = "payerName")
    @Mapping(source = "source.payer.accountId", target = "payerAccountId")
    @Mapping(source = "source.payer.document.value", target = "payerDocument")
    @Mapping(source = "source.payer.document.documentType", target = "payerDocumentType")
    @Mapping(source = "source.receiver.userName", target = "receiverName")
    @Mapping(source = "source.receiver.accountId", target = "receiverAccountId")
    @Mapping(source = "source.receiver.document.value", target = "receiverDocument")
    @Mapping(source = "source.receiver.document.documentType", target = "receiverDocumentType")
    RemittancePayloadOutR map (Remittance source);
}