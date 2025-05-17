package com.zd.demodeploycicd.repository;

import com.zd.demodeploycicd.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
}