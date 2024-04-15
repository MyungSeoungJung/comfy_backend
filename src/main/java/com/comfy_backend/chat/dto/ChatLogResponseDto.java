package com.comfy_backend.chat.dto;
import lombok.*;
import java.time.ZonedDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatLogResponseDto {
    private List<Message> Messages;


    @Builder
    @Getter
    @AllArgsConstructor
    public static class Message {
        private Long id;

        private Long userid;

        private String profileImg;

        private Long roomId;

        private String content;

        private ZonedDateTime createdAt;
    }
}
