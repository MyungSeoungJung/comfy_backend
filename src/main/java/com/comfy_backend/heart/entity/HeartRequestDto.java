package com.comfy_backend.heart.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HeartRequestDto {

    private Long memberId;
    private Long boardId;
    @Builder
    public HeartRequestDto(Long memberId, Long boardId) {
        this.memberId = memberId;
        this.boardId = boardId;
    }
}