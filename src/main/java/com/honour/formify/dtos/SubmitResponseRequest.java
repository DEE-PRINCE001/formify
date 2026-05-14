package com.honour.formify.dtos;

import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.util.List;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubmitResponseRequest {
    private Long formId;

    @NotEmpty(message = "Answers cannot be empty")
    @Valid
    private List<AnswerRequest> answers;
}

