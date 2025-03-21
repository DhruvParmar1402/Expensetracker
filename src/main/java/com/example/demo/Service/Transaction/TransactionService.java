package com.example.demo.Service.Transaction;

import com.example.demo.DTO.*;
import com.example.demo.Entity.TransactionEntity;
import com.example.demo.Entity.UserEntity;
import org.springframework.context.MessageSource;
import com.example.demo.Exception.CustomException;
import com.example.demo.Repo.TransactionRepo;
import com.example.demo.Repo.UserRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
public class TransactionService implements TransactionServiceInterface {
    @Autowired
    private TransactionRepo transactionRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private MessageSource messageSource;

    public List<TransactionsDto> getAll(UserDto userDto) throws CustomException {

        return getByEmail(userDto.getEmail());
    }

    public TransactionsDto save(TransactionsDto transactionsDto, Principal principal) throws CustomException {

        String email = principal.getName();


        List<UserEntity> userEntityList = userRepo.getUSerByEmail(email);

        if (userEntityList.isEmpty()) {
            throw new CustomException(messageSource.getMessage("user.not.found", null, LocaleContextHolder.getLocale()));
        }
        if (userEntityList.getFirst().getBalance() < transactionsDto.getAmount() && transactionsDto.getType().equals("expense")) {
            throw new CustomException(messageSource.getMessage("transaction.amount.exceeds.balance", null, LocaleContextHolder.getLocale()));
        }

        transactionsDto.setUserEmail(email);
        TransactionEntity transactionEntity = modelMapper.map(transactionsDto, TransactionEntity.class);
        userRepo.updateBalanceByTransaction(transactionEntity);
        transactionRepo.save(transactionEntity);
        return modelMapper.map(transactionEntity, TransactionsDto.class);
    }

    public List<TransactionsDto> getByEmail(String email) throws CustomException {
        List<TransactionsDto> transactionsDto = transactionRepo.getTransactionByEmail(email)
                .stream()
                .map((transactionEntity) -> modelMapper.map(transactionEntity, TransactionsDto.class))
                .toList();
        if (transactionsDto.isEmpty()) {
            throw new CustomException(messageSource.getMessage("transaction.not.found", null, LocaleContextHolder.getLocale()));
        }
        return transactionsDto;
    }

    public List<TransactionsDto> getTransactionByEmailAndDate(EmailDateRequest request, Principal principal) throws CustomException {
        String email = principal.getName();
        List<UserEntity> userEntityList = userRepo.getUSerByEmail(email);

        if (userEntityList.isEmpty()) {
            throw new CustomException(messageSource.getMessage("user.not.found", null, LocaleContextHolder.getLocale()));
        }
        return transactionRepo.getTransactionByEmailAndDate(request,principal.getName()).stream().map((transactionEntity) -> modelMapper.map(transactionEntity, TransactionsDto.class)).toList();
    }

    public List<TransactionsDto> getTransactionByEmailAndCategory(EmailCategoryRequest request, Principal principal) throws CustomException {
        String email = principal.getName();
        List<UserEntity> userEntityList = userRepo.getUSerByEmail(email);

        if (userEntityList.isEmpty()) {
            throw new CustomException(messageSource.getMessage("user.not.found", null, LocaleContextHolder.getLocale()));
        }

        return transactionRepo.getTransactionByEmailAndCategory(request,principal.getName()).stream().map((transactionEntity -> modelMapper.map(transactionEntity, TransactionsDto.class))).toList();
    }

    public List<TransactionsDto> getTransactionByEmailAndType(EmailCategoryRequest request, Principal principal) throws CustomException {
        String email = principal.getName();
        List<UserEntity> userEntityList = userRepo.getUSerByEmail(email);

        if (userEntityList.isEmpty()) {
            throw new CustomException(messageSource.getMessage("user.not.found", null, LocaleContextHolder.getLocale()));
        }

        return transactionRepo.getTransactionByEmailAndType(request,principal.getName()).stream().map((transactionEntity -> modelMapper.map(transactionEntity, TransactionsDto.class))).toList();
    }

    public void deleteTransaction(String tid) {
        transactionRepo.deleteTransaction(tid);
    }
}