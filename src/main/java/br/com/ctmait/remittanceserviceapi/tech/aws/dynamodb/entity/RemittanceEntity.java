package br.com.ctmait.remittanceserviceapi.tech.aws.dynamodb.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@DynamoDBTable(tableName = "tb_remittance")
public class RemittanceEntity {

    @DynamoDBHashKey(attributeName = "id")
    private String id;
    @DynamoDBAttribute(attributeName = "value")
    private String value;
    @DynamoDBAttribute(attributeName = "convertedValue")
    private String convertedValue;
    @DynamoDBAttribute(attributeName = "exchangeRate")
    private String exchangeRate;
    @DynamoDBAttribute(attributeName = "exchangeRateDate")
    private String exchangeRateDate;
    @DynamoDBAttribute(attributeName = "payerName")
    private String payerName;
    @DynamoDBAttribute(attributeName = "payerAccountId")
    private String payerAccountId;
    @DynamoDBAttribute(attributeName = "payerDocument")
    private String payerDocument;
    @DynamoDBAttribute(attributeName = "payerDocumentType")
    private String payerDocumentType;
    @DynamoDBAttribute(attributeName = "receiverName")
    private String receiverName;
    @DynamoDBAttribute(attributeName = "receiverAccountId")
    private String receiverAccountId;
    @DynamoDBAttribute(attributeName = "receiverDocument")
    private String receiverDocument;
    @DynamoDBAttribute(attributeName = "receiverDocumentType")
    private String receiverDocumentType;

}