package com.example.demo.Controller;

import com.example.demo.DTO.*;
import com.example.demo.Exception.CustomException;
import com.example.demo.Exception.ResponseHandler;
import com.example.demo.Service.Transaction.TransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private MessageSource messageSource;

    @GetMapping("/get")
    public ResponseEntity<?> getAll(Principal principal) throws CustomException {
        ResponseHandler<List<TransactionsDto>> response=new ResponseHandler<>();

        response.setData(transactionService.getByEmail(principal.getName()));
        response.setStatus(HttpStatus.OK);
        response.setSuccess(true);
        response.setMessage("Transactions fetched successfully");

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping
    public ResponseEntity<?> saveTransaction(@Valid @RequestBody TransactionsDto transactionsDto,Principal principal) throws CustomException {
        ResponseHandler<TransactionsDto> handler=new ResponseHandler<>();

        handler.setStatus(HttpStatus.OK);
        handler.setSuccess(true);
        handler.setMessage(messageSource.getMessage("transaction.saved.success", null, LocaleContextHolder.getLocale()));
        handler.setData(transactionService.save(transactionsDto,principal));

        return ResponseEntity.status(HttpStatus.OK).body(handler);
    }

    @PostMapping("/filterByDates")
    public ResponseEntity<?> getTransactionByEmailAndDate(@Valid @RequestBody EmailDateRequest request,Principal principal) throws CustomException {
        List<TransactionsDto>transactionsDtos=transactionService.getTransactionByEmailAndDate(request,principal);

        ResponseHandler<List<TransactionsDto>> response=new ResponseHandler<>();
        response.setMessage(messageSource.getMessage("transaction.fetched.email.date.success", null, LocaleContextHolder.getLocale()));
        response.setData(transactionsDtos);
        response.setStatus(HttpStatus.OK);
        response.setSuccess(true);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/filterByCategory")
    public ResponseEntity<?> getTransactionByEmailAndCategory (@Valid @RequestBody EmailCategoryRequest emailCategoryRequest,Principal principal) throws CustomException {
        List<TransactionsDto>transactionsDtos=transactionService.getTransactionByEmailAndCategory(emailCategoryRequest,principal);

        ResponseHandler<List<TransactionsDto>> response=new ResponseHandler<>();
        response.setMessage(messageSource.getMessage("transaction.fetched.email.category.success", null, LocaleContextHolder.getLocale()));
        response.setData(transactionsDtos);
        response.setStatus(HttpStatus.OK);
        response.setSuccess(true);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/filterByType")
    public ResponseEntity<?> getTransactionByEmailAndType(@Valid @RequestBody EmailCategoryRequest emailCategoryRequest,Principal principal) throws CustomException {
        List<TransactionsDto>transactionsDtos=transactionService.getTransactionByEmailAndType(emailCategoryRequest,principal);

        ResponseHandler<List<TransactionsDto>> response=new ResponseHandler<>();
        response.setMessage(messageSource.getMessage("transaction.fetched.email.type.success", null, LocaleContextHolder.getLocale()));
        response.setData(transactionsDtos);
        response.setStatus(HttpStatus.OK);
        response.setSuccess(true);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{tid}")
    public ResponseEntity<?> deleteTransaction(@PathVariable String tid)
    {
        transactionService.deleteTransaction(tid);

        ResponseHandler<String> response=new ResponseHandler<>();
        response.setSuccess(true);
        response.setStatus(HttpStatus.OK);
        response.setMessage(messageSource.getMessage("transaction.deleted.success", null, LocaleContextHolder.getLocale()));

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
