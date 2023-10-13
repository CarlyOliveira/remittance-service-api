package br.com.ctmait.remittanceserviceapi.tech.aws.dynamodb.config;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;

import java.time.LocalDate;
import java.util.Objects;

public class LocalDateConverter implements DynamoDBTypeConverter<String, LocalDate> {

    @Override
    public String convert(LocalDate localDate) {
        return Objects.nonNull(localDate) ? localDate.toString() : null;
    }

    @Override
    public LocalDate unconvert(String s) {
        return Objects.nonNull(s) ? LocalDate.parse(s) : null;
    }
}
