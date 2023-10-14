package br.com.ctmait.remittanceserviceapi.tech.rest.mapper;

import br.com.ctmait.remittanceserviceapi.domain.models.remittance.Remittance;
import br.com.ctmait.remittanceserviceapi.tech.rest.payload.in.RemittancePayloadIn;
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
}
