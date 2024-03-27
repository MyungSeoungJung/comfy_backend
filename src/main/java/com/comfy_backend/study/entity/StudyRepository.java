package com.comfy_backend.study.entity;

import com.comfy_backend.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudyRepository extends JpaRepository<Study, Long> {


    Optional<User> findByCreatorNickName(String creatorNickName);


}
