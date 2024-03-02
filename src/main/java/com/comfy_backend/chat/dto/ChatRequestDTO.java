package com.comfy_backend.chat.dto;

import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class ChatRequestDTO {
    private Long myId;

    private Long toUserId;

    private Long roomId;

    private String message;

    private ZonedDateTime lastMsgTime;

    private String nickName;
}
