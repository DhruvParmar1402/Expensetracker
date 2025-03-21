package com.example.demo.Config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class AWSCredentials {

    @Bean
    public DynamoDBMapper buildMapper(){
        return new DynamoDBMapper(getCredentials());
    }

    public AmazonDynamoDB getCredentials() {
        BasicAWSCredentials awsCreds = new BasicAWSCredentials("es22bb", "bs2i1g");

        return  AmazonDynamoDBClientBuilder.standard()
                .withEndpointConfiguration(
                        new AwsClientBuilder.EndpointConfiguration("http://localhost:8000", "us-east-1")
                )
                .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                .build();
    }
}
