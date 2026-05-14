package com.honour.formify.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.honour.formify.entity.Answer;

public interface AnswerRepository extends JpaRepository<Answer, Long> {}