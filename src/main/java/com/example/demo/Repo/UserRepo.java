package com.example.demo.Repo;


import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.example.demo.Entity.TransactionEntity;
import com.example.demo.Entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserRepo {

    @Autowired
    private DynamoDBMapper mapper;

    public void save(UserEntity userEntity) {
        mapper.save(userEntity);
    }

    public List<UserEntity> getUSerByEmail(String email) {
        Map<String, AttributeValue> eav = new HashMap<>();
        eav.put(":email", new AttributeValue().withS(email));

        DynamoDBQueryExpression<UserEntity> queryExpression = new DynamoDBQueryExpression<UserEntity>()
                .withIndexName("EmailIndex")
                .withKeyConditionExpression("email = :email")
                .withExpressionAttributeValues(eav)
                .withConsistentRead(false)
                .withScanIndexForward(false);

        return mapper.query(UserEntity.class, queryExpression);
    }

    public void delete(UserEntity userEntity) {
        mapper.delete(userEntity);
    }

    public void updateBalanceByTransaction(TransactionEntity transactionEntity) {
        UserEntity user=getUSerByEmail(transactionEntity.getUserEmail()).getFirst();
        if(transactionEntity.getType().toLowerCase().equals("income"))
        {
            user.setBalance(user.getBalance()+transactionEntity.getAmount());
        }
        else{
            user.setBalance(user.getBalance()-transactionEntity.getAmount());
        }
        save(user);
    }
}
