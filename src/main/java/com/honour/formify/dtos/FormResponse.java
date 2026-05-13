package com.honour.formify.dtos;

import java.util.List;

import lombok.Builder;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FormResponse {
    private Long id;
    private String title;
    private String description;
    private List<QuestionDTO> questions;
}


