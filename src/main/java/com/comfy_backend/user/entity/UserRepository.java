package com.comfy_backend.user.entity;

import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
@Primary
public interface UserRepository extends JpaRepository<User,Long> {

}
