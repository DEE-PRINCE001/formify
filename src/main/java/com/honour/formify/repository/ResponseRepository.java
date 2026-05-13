package com.honour.formify.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.honour.formify.entity.Response;
import java.util.List;

public interface ResponseRepository extends JpaRepository<Response, Long> {
    List<Response> findByFormId(Long formId);
}