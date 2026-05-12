package com.honour.formify.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.honour.formify.dtos.FormRequest;
import com.honour.formify.dtos.FormResponse;
import com.honour.formify.service.FormService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/forms")
@RequiredArgsConstructor
public class FormController {
    private final FormService formService;

    @PostMapping
    public ResponseEntity<FormResponse> createForm(@RequestBody FormRequest request) {
        return ResponseEntity.ok(formService.createForm(request));
    }

    @GetMapping
    public ResponseEntity<List<FormResponse>> getMyForms() {
        return ResponseEntity.ok(formService.getAllMyForms());
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteForm(@PathVariable Long id) {
        formService.deleteForm(id);
        return ResponseEntity.noContent().build();
    }
}
