package com.example.demo.Service.Transaction;

import com.example.demo.DTO.EmailCategoryRequest;
import com.example.demo.DTO.EmailDateRequest;
import com.example.demo.DTO.TransactionsDto;
import com.example.demo.DTO.UserDto;
import com.example.demo.Exception.CustomException;

import java.security.Principal;
import java.util.List;

public interface TransactionServiceInterface {
    public List<TransactionsDto> getAll(UserDto userDto) throws CustomException;
    public TransactionsDto save(TransactionsDto transactionsDto, Principal principal) throws CustomException;
    public List<TransactionsDto> getByEmail(String email) throws CustomException;
    public List<TransactionsDto> getTransactionByEmailAndDate(EmailDateRequest request, Principal principal) throws CustomException;
    public List<TransactionsDto> getTransactionByEmailAndCategory(EmailCategoryRequest request, Principal principal) throws CustomException;
    public List<TransactionsDto> getTransactionByEmailAndType(EmailCategoryRequest request, Principal principal) throws CustomException;
    public void deleteTransaction(String tid);
}
