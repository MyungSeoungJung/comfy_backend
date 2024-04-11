package com.comfy_backend.chat.dto;

import com.comfy_backend.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.time.ZonedDateTime;
import java.util.List;
@Data
@Builder
public class ChatListResponseDto {

    private User myUser;

    private List<Room> rooms;

    @Builder
    @Getter
    @AllArgsConstructor
    public static class Room {

        private Long toUserId;

        private String toUserNick;

        private String toUserImg;

        private Long roomId;

        private Boolean isRead;

        private String lastMsg;

        private ZonedDateTime lastMsgTime;
    }

}
