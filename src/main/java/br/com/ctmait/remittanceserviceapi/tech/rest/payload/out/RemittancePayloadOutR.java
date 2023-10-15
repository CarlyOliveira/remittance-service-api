package br.com.ctmait.remittanceserviceapi.tech.rest.payload.out;

public record RemittancePayloadOutR(String id,
                                    String value,
                                    String convertedValue,
                                    String exchangeRate,
                                    String exchangeRateDate,
                                    String payerName,
                                    String payerAccountId,
                                    String payerDocument,
                                    String payerDocumentType,
                                    String receiverName,
                                    String receiverAccountId,
                                    String receiverDocument,
                                    String receiverDocumentType) {

}
