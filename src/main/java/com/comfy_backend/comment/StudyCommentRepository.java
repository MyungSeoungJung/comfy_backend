package com.comfy_backend.comment;

import com.comfy_backend.study.entity.Study;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudyCommentRepository extends JpaRepository<StudyComment, Long> {

    List<StudyComment> findByStudy(Optional<Study> study);
}
