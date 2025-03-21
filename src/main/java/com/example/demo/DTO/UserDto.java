package com.example.demo.DTO;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class UserDto {

    @NotBlank(message = "{user.name.notBlank}")
    @Size(min = 3, max = 50, message = "{user.name.size}")
    private String name;

    @NotBlank(message = "{user.email.notBlank}")
    @Email(message = "{user.email.invalid}")
    private String email;

    @NotBlank(message = "{user.password.notBlank}")
    private String password;

    @PositiveOrZero(message = "{user.balance.positive}")
    private double balance;

    private LocalDate date=LocalDate.now();
}
