package com.comfy_backend.tag.studyHashTagMapping.entity;

import com.comfy_backend.study.entity.Study;
import com.comfy_backend.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StudyHashTagRepository extends JpaRepository<HashTagMapping, Long> {
    List<HashTagMapping> findAllByStudy(Study study);

    @Query("SELECT hm FROM HashTagMapping hm WHERE hm.study.id = :studyId")
    List<HashTagMapping> findByStudyId(@Param("studyId") long studyId);
}
