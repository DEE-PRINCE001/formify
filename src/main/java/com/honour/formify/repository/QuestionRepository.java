package com.honour.formify.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.honour.formify.entity.Question;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    
}
