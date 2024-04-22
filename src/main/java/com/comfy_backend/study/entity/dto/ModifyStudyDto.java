package com.comfy_backend.study.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ModifyStudyDto {
    private long id;
    private String title;
    private String content;
    private List<String> tagNames;

    private String recruitStatus;
}
