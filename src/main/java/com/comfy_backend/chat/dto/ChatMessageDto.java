package com.comfy_backend.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageDto {
    // 메시지  타입 : 입장, 채팅
    public enum MessageType{
        ENTER, TALK
    }
    private MessageType messageType; // 메시지 타입
    private Long chatRoomId; // 방 번호
    private Long sender; // 채팅을 보낸 사람
    private String message; // 메시지
}
