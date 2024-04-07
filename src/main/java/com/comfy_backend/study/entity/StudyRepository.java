package com.comfy_backend.study.entity;

import com.comfy_backend.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StudyRepository extends JpaRepository<Study, Long> {

    Optional<User> findByCreatorNickName(String creatorNickName);
    @Query("SELECT s FROM Study s WHERE s.totalHeart > 0 AND s.recruitStatus = '모집중' ORDER BY s.totalHeart DESC")
    List<Study> findTop5ByTotalHeartGreaterThanOrderByTotalHeartDesc();}
