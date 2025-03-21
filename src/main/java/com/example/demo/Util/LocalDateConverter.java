package com.example.demo.Util;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateConverter implements DynamoDBTypeConverter<String, LocalDate> {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;

    @Override
    public String convert(LocalDate date) {
        return date.format(formatter);
    }

    @Override
    public LocalDate unconvert(String dateString) {
        return LocalDate.parse(dateString, formatter);
    }
}
