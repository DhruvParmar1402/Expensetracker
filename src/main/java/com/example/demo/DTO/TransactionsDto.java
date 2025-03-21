package com.example.demo.DTO;

import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class    TransactionsDto {

    private String transactionId;

    private String userEmail;

    @Positive(message = "{transaction.amount.positive}")
    private double amount;

    @NotBlank(message = "{transaction.category.notBlank}")
    private String category;

    @NotBlank(message = "{transaction.type.invalid}")
    @Pattern(regexp = "income|expense", message = "{transaction.type.invalid}")
    private String type;

    @NotBlank(message = "{transaction.description.notBlank}")
    private String description;

    @PastOrPresent(message = "{transaction.date.past_or_present}")
    private LocalDate date;
}
