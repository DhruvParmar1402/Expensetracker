package com.example.demo.DTO;

import lombok.*;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailDateRequest {
    private String startDate;
    private String endDate;
}
