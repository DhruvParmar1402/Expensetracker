package com.example.demo.Controller;

import com.example.demo.DTO.LoginDto;
import com.example.demo.DTO.UserDto;
import com.example.demo.Exception.CustomException;
import com.example.demo.Exception.ResponseHandler;
import com.example.demo.Service.User.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService authService;

    @Autowired
    private MessageSource messageSource;


    @PostMapping("/register")
    public ResponseEntity<?> addUser(@Valid @RequestBody UserDto userDto) throws CustomException {
        authService.save(userDto);

        ResponseHandler<UserDto> response = new ResponseHandler<>();
        response.setStatus(HttpStatus.OK);
        response.setSuccess(true);
        response.setMessage(messageSource.getMessage("user.saved.success", null, LocaleContextHolder.getLocale()));
        response.setData(userDto);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDto user) {
        return new ResponseEntity<>(authService.login(user), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> updateUser(@Valid @RequestBody UserDto userDto,Principal principal) throws CustomException {
        authService.update(userDto,principal.getName());

        ResponseHandler<UserDto> response = new ResponseHandler<>();
        response.setStatus(HttpStatus.OK);
        response.setSuccess(true);
        response.setMessage(messageSource.getMessage("user.updated.success", null, LocaleContextHolder.getLocale()));
        response.setData(userDto);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUser( Principal principal) throws CustomException {
        authService.delete(principal.getName());

        ResponseHandler<String> response = new ResponseHandler<>();
        response.setStatus(HttpStatus.OK);
        response.setSuccess(true);
        response.setMessage(messageSource.getMessage("user.deleted.success", null, LocaleContextHolder.getLocale()));

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
