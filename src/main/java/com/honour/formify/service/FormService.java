package com.honour.formify.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.honour.formify.dtos.FormRequest;
import com.honour.formify.dtos.FormResponse;
import com.honour.formify.dtos.QuestionDTO;
import com.honour.formify.dtos.QuestionRequest;
import com.honour.formify.entity.Form;
import com.honour.formify.entity.Option;
import com.honour.formify.entity.Question;
import com.honour.formify.entity.QuestionType;
import com.honour.formify.entity.User;
import com.honour.formify.repository.FormRepository;
import com.honour.formify.repository.QuestionRepository;
import com.honour.formify.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FormService {
    private final FormRepository formRepository;
    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;

    @Transactional
    public FormResponse createForm(FormRequest request){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Form form = Form.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .createdBy(user)
                .createdAt(LocalDateTime.now())
                .build();

        if (request.getQuestions() != null) {
            for (QuestionRequest qReq : request.getQuestions()) {
                Question question = Question.builder()
                        .text(qReq.getText())
                        .type(QuestionType.valueOf(qReq.getType().toUpperCase()))
                        .build();
                if (qReq.getOptions() != null) {
                    for (String optText : qReq.getOptions()) {
                        Option option = Option.builder().text(optText).build();

                        question.addOption(option);
                    }
                }

                form.addQuestion(question);
            }
        }
        Form savedForm = formRepository.save(form);
        return mapToResponse(savedForm);
    }


    public List<FormResponse> getAllMyForms() {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByEmail(email).orElseThrow();

        return formRepository.findByCreatedById(user.getId())
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());

    }


    public void deleteForm(Long id){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByEmail(email).orElseThrow();

        Form form = formRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Form not found"));

        if (!form.getCreatedBy().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized");
        }

        formRepository.delete(form);
    }


    private FormResponse mapToResponse(Form form) {

        return FormResponse.builder()
        .id(form.getId())
        .title(form.getTitle())
        .description(form.getDescription())
        .questions(form.getQuestions().stream()
        .map(this::mapQuestionToDto)
        .collect(Collectors.toList()))
        .build();

    }

    private QuestionDTO mapQuestionToDto(Question question) {
        QuestionDTO dto = new QuestionDTO();
        dto.setId(question.getId());
        dto.setText(question.getText());
        dto.setType(question.getType().name());
        dto.setOptions(question.getOptions().stream()
                .map(Option::getText)
                .collect(Collectors.toList()));
        return dto;
    }


    
    
}
