package com.example.demo.Entity;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.example.demo.Util.LocalDateConverter;
import lombok.*;

import java.time.LocalDate;

@DynamoDBTable(tableName = "transactions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionEntity {

    @DynamoDBHashKey(attributeName = "transactionId")
    @DynamoDBAutoGeneratedKey
    private String transactionId;

    @DynamoDBIndexHashKey(globalSecondaryIndexName = "UserTransactionsIndex", attributeName = "userEmail")
    @DynamoDBAttribute(attributeName = "userEmail")
    private String userEmail;

    @DynamoDBAttribute(attributeName = "amount")
    private Double amount;

    @DynamoDBAttribute(attributeName = "category")
    private String category;

    @DynamoDBAttribute(attributeName = "type")
    private String type;

    @DynamoDBAttribute(attributeName = "description")
    private String description;

    @DynamoDBIndexRangeKey(globalSecondaryIndexName = "UserTransactionsIndex", attributeName = "date")
    @DynamoDBTypeConverted(converter = LocalDateConverter.class)
    @DynamoDBAttribute(attributeName = "date")
    private LocalDate date;
}
