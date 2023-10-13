package br.com.ctmait.remittanceserviceapi.tech.aws.dynamodb.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile({"local"})
public class DynamoDbConfigurationLocal {

    @Value("${aws.region.static}")
    private String awsRegion;
    @Value("${aws.dynamodb.endpoint}")
    private String serviceEndpoint;
    @Value("${aws.credentials.secret-key}")
    private String secretKey;
    @Value("${aws.credentials.access-key}")
    private String accessKey;


    @Bean
    public AmazonDynamoDB amazonDynamoDB(){
        final AwsClientBuilder.EndpointConfiguration endpointConfiguration = new AwsClientBuilder.EndpointConfiguration(serviceEndpoint,awsRegion);
        final BasicAWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey );
        return AmazonDynamoDBClientBuilder
                .standard()
                .withEndpointConfiguration(endpointConfiguration)
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .build();
    }

    @Bean
    public DynamoDBMapper dynamoDBMapper(final AmazonDynamoDB amazonDynamoDB){
        return new DynamoDBMapper(amazonDynamoDB);
    }

    @Bean
    public DynamoDB dynamoDB(final AmazonDynamoDB amazonDynamoDB){
        return new DynamoDB(amazonDynamoDB);
    }
}
