package com.comfy_backend.heart.entity;

import com.comfy_backend.heart.entity.Heart;
import com.comfy_backend.study.entity.Study;
import com.comfy_backend.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface HeartRepository extends JpaRepository<Heart, Long> {

    Heart findById(long id);
    Optional<Heart> findByUserAndStudy(User user, Study study);
    @Query("SELECT CASE WHEN COUNT(h) > 0 THEN true ELSE false END FROM Heart h WHERE h.user.id = :userId AND h.study.id = :studyId")
    boolean isUserLikedStudy(Long userId, Long studyId);

}
