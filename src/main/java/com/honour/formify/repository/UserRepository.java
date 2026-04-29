package com.honour.formify.repository;

import com.honour.formify.entity.User;

public interface UserRepository extends org.springframework.data.jpa.repository.JpaRepository<User, Long> {
    java.util.Optional<User> findByEmail(String email);
   
}
