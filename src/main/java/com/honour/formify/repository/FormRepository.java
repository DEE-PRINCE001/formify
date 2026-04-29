package com.honour.formify.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.honour.formify.entity.Form;

public interface FormRepository extends JpaRepository<Form, Long> {

    java.util.List<Form> findByCreatedById(Long userId);
    
}
