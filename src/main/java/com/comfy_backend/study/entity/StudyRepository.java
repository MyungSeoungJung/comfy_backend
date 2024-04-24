package com.comfy_backend.study.entity;

import com.comfy_backend.tag.hashTag.entity.HashTag;
import com.comfy_backend.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface StudyRepository extends JpaRepository<Study, Long> {

    Optional<User> findByCreatorNickName(String creatorNickName);
    @Query("SELECT s FROM Study s WHERE s.totalHeart > 0 AND s.recruitStatus = '모집중' ORDER BY s.totalHeart DESC")
    List<Study> findTop5ByTotalHeartGreaterThanOrderByTotalHeartDesc();

    Page<Study> findAll(Pageable pageable);

    Page<Study> findByRecruitStatus(String state, Pageable pageable);

    Page<Study> findByCreatorId(Long userId, Pageable pageable);

    List<Study> findAllByHashTagMappings_HashTagIn(Set<HashTag> hashTags);


    Page<Study> findByRecruitStatusOrderByCreatedTimeDesc(String state, Pageable pageable); //댓글 순

    Page<Study> findByRecruitStatusOrderByTotalHeartDesc(String state, Pageable pageable); // 좋아요순
    Page<Study> findByRecruitStatusOrderByTotalCommentDesc(String state, Pageable pageable); //최신순


}


