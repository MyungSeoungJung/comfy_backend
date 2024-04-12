package com.comfy_backend.heart.entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HeartRequestDto {

    private Long id;
    private Long studyId;

    public HeartRequestDto(Long id, Long studyId) {
        this.id = id;
        this.studyId = studyId;
    }
}