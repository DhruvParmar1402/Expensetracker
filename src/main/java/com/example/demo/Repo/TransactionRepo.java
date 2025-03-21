package com.example.demo.Repo;

import com.amazonaws.retry.PredefinedRetryPolicies;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.example.demo.DTO.EmailCategoryRequest;
import com.example.demo.DTO.EmailDateRequest;
import com.example.demo.Entity.TransactionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class TransactionRepo {

    @Autowired
    private DynamoDBMapper mapper;


    public void save(TransactionEntity transactionEntity) {
        mapper.save(transactionEntity);
    }


    public List<TransactionEntity> getTransactionByEmail(String email) {
        Map<String, AttributeValue> eav = new HashMap<>();
        eav.put(":email", new AttributeValue().withS(email));

        DynamoDBQueryExpression<TransactionEntity> queryExpression = new DynamoDBQueryExpression<TransactionEntity>()
                .withIndexName("UserTransactionsIndex")
                .withKeyConditionExpression("userEmail = :email")
                .withExpressionAttributeValues(eav)
                .withConsistentRead(false)
                .withScanIndexForward(false);
        return mapper.query(TransactionEntity.class, queryExpression);
    }

    public List<TransactionEntity> getTransactionByEmailAndDate(EmailDateRequest request,String email) {
        Map<String, AttributeValue> eav = new HashMap<>();
        eav.put(":email", new AttributeValue().withS(email));
        eav.put(":startDate", new AttributeValue().withS(request.getStartDate()));
        eav.put(":endDate", new AttributeValue().withS(request.getEndDate()));

        Map<String, String> expressionAttributeNames = new HashMap<>();
        expressionAttributeNames.put("#dt", "date");

        DynamoDBQueryExpression<TransactionEntity> queryExpression = new DynamoDBQueryExpression<TransactionEntity>()
                .withIndexName("UserTransactionsIndex")
                .withKeyConditionExpression("userEmail = :email AND #dt BETWEEN :startDate AND :endDate")
                .withExpressionAttributeNames(expressionAttributeNames)
                .withExpressionAttributeValues(eav)
                .withConsistentRead(false)
                .withScanIndexForward(false);

        return mapper.query(TransactionEntity.class, queryExpression);
    }

    public List<TransactionEntity> getTransactionByEmailAndCategory(EmailCategoryRequest request,String email) {
        Map<String,AttributeValue> eav=new HashMap<>();
        eav.put(":email",new AttributeValue().withS(email));
        eav.put(":category",new AttributeValue().withS(request.getCategory()));
        String filterCondition="category=:category";

        DynamoDBQueryExpression<TransactionEntity> queryExpression = new DynamoDBQueryExpression<TransactionEntity>()
                .withIndexName("UserTransactionsIndex")
                .withKeyConditionExpression("userEmail = :email")
                .withFilterExpression(filterCondition)
                .withExpressionAttributeValues(eav)
                .withConsistentRead(false)
                .withScanIndexForward(false);
        return mapper.query(TransactionEntity.class,queryExpression);
    }

    public List<TransactionEntity> getTransactionByEmailAndType(EmailCategoryRequest request,String email) {
        Map<String, AttributeValue> eav = new HashMap<>();
        eav.put(":email", new AttributeValue().withS(email));
        eav.put(":type",new AttributeValue().withS(request.getCategory()));

        Map<String, String> expressionAttributeNames = new HashMap<>();
        expressionAttributeNames.put("#typ", "type");

        String filterExp="#typ = :type";

        DynamoDBQueryExpression<TransactionEntity> queryExpression = new DynamoDBQueryExpression<TransactionEntity>()
                .withIndexName("UserTransactionsIndex")
                .withKeyConditionExpression("userEmail = :email")
                .withFilterExpression(filterExp)
                .withExpressionAttributeNames(expressionAttributeNames)
                .withExpressionAttributeValues(eav)
                .withConsistentRead(false)
                .withScanIndexForward(false);

        return mapper.query(TransactionEntity.class, queryExpression);
    }

    public void deleteTransaction(String tid) {
        TransactionEntity transactionEntity=mapper.load(TransactionEntity.class,tid);
        mapper.delete(transactionEntity);
    }
}
