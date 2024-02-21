package com.comfy_backend.user.entity;

import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@Primary
public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByEmail(String email);
}
