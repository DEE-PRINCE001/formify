package com.honour.formify.dtos;

import java.util.List;
import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuestionRequest {
    @NotBlank(message = "Question text is required")
    private String text;

    @NotBlank(message = "Question type is required")
    private String type; 
    private List<String> options; 

    private boolean required;
}