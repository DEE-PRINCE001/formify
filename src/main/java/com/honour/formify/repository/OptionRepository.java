package com.honour.formify.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.honour.formify.entity.Option;

public interface OptionRepository extends JpaRepository<Option, Long>   {
    
}
