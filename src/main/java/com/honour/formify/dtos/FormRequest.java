package com.honour.formify.dtos;

import java.util.List;

import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FormRequest {
    private String title;
    private String description; 
    private List<QuestionRequest> questions;
}


