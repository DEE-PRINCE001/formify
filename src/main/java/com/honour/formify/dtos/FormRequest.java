package com.honour.formify.dtos;

import java.util.List;

import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FormRequest {

    @NotBlank(message = "Title is required")
    private String title;

    @Size(max = 200, message = "Description must be at most 200 characters")
    private String description; 

    @NotNull(message = "At least one question is required")
    private List<QuestionRequest> questions;
}


