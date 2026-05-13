package com.honour.formify.dtos;

import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FormResponseDetail {
    private Long responseId;
    private LocalDateTime submittedAt;
    private List<AnswerDetail> answers;
}
