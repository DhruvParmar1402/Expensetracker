package com.example.demo.Service.User;

import com.example.demo.DTO.UserDto;
import com.example.demo.Exception.CustomException;

public interface UserInterface {
    public void save(UserDto userDto) throws CustomException;
    public void update(UserDto userDto,String email) throws CustomException;
    public void delete(String email) throws CustomException;

}
