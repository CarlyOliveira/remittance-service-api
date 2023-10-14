package br.com.ctmait.remittanceserviceapi.tech.aws.dynamodb.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@DynamoDBTable(tableName = "tb_user")
public class UserEntity {

    @DynamoDBHashKey(attributeName = "document")
    private String document;
    @DynamoDBAttribute(attributeName = "password")
    private String password;
    @DynamoDBAttribute(attributeName = "id")
    private String id;
    @DynamoDBAttribute(attributeName = "documentType")
    private String documentType;
    @DynamoDBAttribute(attributeName = "email")
    private String email;
    @DynamoDBAttribute(attributeName = "name")
    private String name;

}