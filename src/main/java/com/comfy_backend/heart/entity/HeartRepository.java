package com.comfy_backend.heart.entity;

import com.comfy_backend.heart.entity.Heart;
import com.comfy_backend.study.entity.Study;
import com.comfy_backend.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HeartRepository extends JpaRepository<Heart, Long> {

    Heart findById(long id);
    Optional<Heart> findByUserAndStudy(User user, Study study);
}
