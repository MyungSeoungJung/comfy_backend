package com.comfy_backend.comment;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StudyCommentRepository extends JpaRepository<StudyComment, Long> {
}
