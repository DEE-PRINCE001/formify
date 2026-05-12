package com.honour.formify.dtos;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionDTO {
    private Long id;
    private String text;
    private String type; 
    private List<String> options; 
}