package com.example.demo.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Setter
@Getter
public class EmailCategoryRequest {

    @NotBlank(message = "{transaction.category.notBlank}")
    private String category;

}
