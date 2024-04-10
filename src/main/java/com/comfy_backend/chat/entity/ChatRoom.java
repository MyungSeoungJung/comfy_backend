package com.comfy_backend.chat.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.ZonedDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long myUserId;

    private Long toUserId;

    private String toUserNick;
    @Column(columnDefinition = "LONGTEXT")
    private String toUserImg; //이미지를 좀 작게해야됨

    private Long roomId;

    private String lastMsg;

    private Boolean isRead;

    private ZonedDateTime lastMsgTime;
}