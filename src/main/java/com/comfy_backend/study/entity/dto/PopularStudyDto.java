package com.comfy_backend.study.entity.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PopularStudyDto {
    private String title;
    private String userNickName;
    @Column(columnDefinition = "LONGTEXT")
    private String userImg;
}
