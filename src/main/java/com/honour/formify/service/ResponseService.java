package com.honour.formify.service;

import java.util.List;
import java.util.Optional;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.security.core.context.SecurityContextHolder;
import com.honour.formify.dtos.AnswerDetail;
import com.honour.formify.dtos.AnswerRequest;
import com.honour.formify.dtos.FormResponseDetail;
import com.honour.formify.dtos.SubmitResponseRequest;
import com.honour.formify.entity.Answer;
import com.honour.formify.entity.Form;
import com.honour.formify.entity.Question;
import com.honour.formify.entity.Response;
import com.honour.formify.entity.User;
import com.honour.formify.repository.FormRepository;
import com.honour.formify.repository.OptionRepository;
import com.honour.formify.repository.QuestionRepository;
import com.honour.formify.repository.ResponseRepository;
import com.honour.formify.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ResponseService {
    private final FormRepository formRepository;
    private final ResponseRepository responseRepository;
    private final QuestionRepository questionRepository;
    private final OptionRepository optionRepository;
    private final UserRepository userRepository;


    @Transactional
    public void submitFormResponse(Long formId, SubmitResponseRequest request) {
        Form form = formRepository.findById(formId)
                .orElseThrow(() -> new RuntimeException("Form not found"));

        for (Question question : form.getQuestions()) {
            if (!question.isRequired()) continue;

            Optional<AnswerRequest> matching = request.getAnswers().stream()
                    .filter(a -> Objects.equals(a.getQuestionId(), question.getId()))
                    .findFirst();

            String answerText = matching
                    .map(AnswerRequest::getAnswerText)
                    .orElse(null);

            if (answerText == null || answerText.isBlank()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Required question not answered");
            }
        }
        Response response = Response.builder()
                .form(form)
                .build();

        
        for (AnswerRequest answerReq : request.getAnswers()) {
            Question question = questionRepository.findById(answerReq.getQuestionId())
                    .orElseThrow(() -> new RuntimeException("Question not found"));

            Answer answer = Answer.builder()
                    .question(question)
                    .answerText(answerReq.getAnswerText())
                    .response(response)
                    .build();
           response.addAnswer(answer);
        }

        responseRepository.save(response);
    }


    public List<FormResponseDetail> getResponsesForForm(Long formId) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();


        User currentUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Form form = formRepository.findById(formId)
                .orElseThrow(() -> new RuntimeException("Form not found"));
        
        if (!form.getCreatedBy().getId().equals(currentUser.getId())) {
            throw new RuntimeException("Unauthorized: You do not own this form");
        }

        List<Response> responses = responseRepository.findByFormId(formId);

        return responses.stream().map(resp -> 
            FormResponseDetail.builder()
                .responseId(resp.getId())
                .submittedAt(resp.getSubmittedAt())
                .answers(resp.getAnswers().stream().map(ans -> 
                    AnswerDetail.builder()
                        .questionText(ans.getQuestion().getText())
                        .answerText(ans.getAnswerText())
                        .build()
                ).collect(Collectors.toList()))
                .build()
        ).collect(Collectors.toList());
    }
}