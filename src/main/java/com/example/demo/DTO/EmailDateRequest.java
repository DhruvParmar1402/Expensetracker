package com.example.demo.DTO;

import jakarta.validation.constraints.PastOrPresent;
import lombok.*;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailDateRequest {

    @PastOrPresent(message = "{transaction.date.past_or_present}")
    private String startDate;

    @PastOrPresent(message = "{transaction.date.past_or_present}")
    private String endDate;
}
