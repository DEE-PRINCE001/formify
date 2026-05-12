package com.honour.formify.dtos;

import java.util.List;

import lombok.Data;

@Data
public class FormRequest {
    private String title;
    private String description; 
    private List<QuestionRequest> questions;
    
}


