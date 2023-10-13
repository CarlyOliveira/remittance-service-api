package br.com.ctmait.remittanceserviceapi.tech.aws.dynamodb.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@DynamoDBTable(tableName = "tb_account")
public class AccountEntity {

    @DynamoDBHashKey(attributeName = "id")
    private String id;
    @DynamoDBAttribute(attributeName = "ownerId")
    private String ownerId;
    @DynamoDBAttribute(attributeName = "balanceValue")
    private String balanceValue;
    @DynamoDBAttribute(attributeName = "balanceCurrency")
    private String balanceCurrency;
    @DynamoDBAttribute(attributeName = "limitValue")
    private String limitValue;
    @DynamoDBAttribute(attributeName = "limitCurrency")
    private String limitCurrency;

}