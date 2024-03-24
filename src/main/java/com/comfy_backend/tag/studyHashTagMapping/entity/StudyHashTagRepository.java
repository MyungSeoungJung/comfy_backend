package com.comfy_backend.tag.studyHashTagMapping.entity;

import com.comfy_backend.study.entity.Study;
import com.comfy_backend.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudyHashTagRepository extends JpaRepository<HashTagMapping, Long> {
    List<HashTagMapping> findAllByStudy(Study study);
}
