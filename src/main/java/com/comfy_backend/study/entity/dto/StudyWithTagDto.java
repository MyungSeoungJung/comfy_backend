package com.comfy_backend.study.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudyWithTagDto {
    private Long id;
    private String title;
    private String content;
    private String recruitStatus;
    private String creatorNickName;
    private LocalDateTime createdTime;
    private Long totalComment;
    private Long totalHeart;
    private List<String> tagNames; // 태그 이름들을 담을 리스트
}