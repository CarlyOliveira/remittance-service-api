package br.com.ctmait.remittanceserviceapi.tech.rest.payload.out;

public record RemittancePayloadOut(String id,
                                   String value,
                                   String valueCurrency,
                                   String convertedValue,
                                   String convertedValueCurrency,
                                   String exchangeRate,
                                   String exchangeRateDate,
                                   String payerName,
                                   String payerAccountId,
                                   String payerDocument,
                                   String payerDocumentType,
                                   String receiverName,
                                   String receiverAccountId,
                                   String receiverDocument,
                                   String receiverDocumentType,
                                   String remittanceCreateDate,
                                   String remittanceStatus) {

}