package br.com.ctmait.remittanceserviceapi.tech.rest.payload.in;


import br.com.ctmait.remittanceserviceapi.domain.models.user.Document;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class PersonPayloadIn {
    private String accountId;
    private Document document;
}