package com.honour.formify.repository;
import org.springframework.stereotype.Repository;

import com.honour.formify.entity.User;

@Repository
public interface UserRepository extends org.springframework.data.jpa.repository.JpaRepository<User, Long> {
    java.util.Optional<User> findByEmail(String email);
   
}
