package com.example.demo.Service.User;

import com.example.demo.DTO.LoginDto;
import com.example.demo.DTO.UserDto;
import com.example.demo.Entity.UserEntity;
import com.example.demo.Exception.CustomException;
import com.example.demo.Repo.UserRepo;
import com.example.demo.Service.UserServiceImpl;
import com.example.demo.Util.JwtUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserInterface {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserServiceImpl userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    public UserService (UserRepo userRepository) {
        this.userRepo = userRepository;
    }

    public void save(UserDto userDto) throws CustomException {
        UserEntity userEntity = modelMapper.map(userDto, UserEntity.class);
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        userRepo.save(userEntity);
    }

    public String login (LoginDto user)
    {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
        return jwtUtil.generateToken(userDetails.getUsername());
    }

    public void update(UserDto userDto,String email) throws CustomException {
        UserEntity userEntity = modelMapper.map(userDto, UserEntity.class);
        List<UserEntity> userEntityList = userRepo.getUSerByEmail(userDto.getEmail());

        if (userEntityList.isEmpty()) {
            throw new CustomException(messageSource.getMessage("user.not.found", null, LocaleContextHolder.getLocale()));
        }

        UserEntity toBeUpdated = userEntityList.getFirst();
        userEntity.setUserId(toBeUpdated.getUserId());

        userRepo.save(userEntity);
    }

    public void delete(String email) throws CustomException {
        List<UserEntity> userEntityList = userRepo.getUSerByEmail(email);

        if (userEntityList.isEmpty()) {
            throw new CustomException(messageSource.getMessage("user.not.found", null, LocaleContextHolder.getLocale()));
        }

        userRepo.delete(userEntityList.getFirst());
    }

}
