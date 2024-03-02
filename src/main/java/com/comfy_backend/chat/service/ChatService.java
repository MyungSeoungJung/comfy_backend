package com.comfy_backend.chat.service;

import com.comfy_backend.chat.dto.ChatRequestDTO;
import com.comfy_backend.chat.entity.ChatMessage;
import com.comfy_backend.chat.entity.ChatRoom;
import com.comfy_backend.chat.repository.ChatRepository;
import com.comfy_backend.chat.repository.RoomRepository;
import com.comfy_backend.user.entity.User;
import com.comfy_backend.user.entity.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatService {

    private final RoomRepository joinRoomRepository;
    private final ChatRepository chatRepository;
    private final UserRepository userRepository;

    @Transactional
    public void createRoom(ChatRequestDTO dto) {
        User toUser = userRepository.findById(dto.getToUserId()).orElseThrow();
        User fromUser = userRepository.findById(dto.getMyId()).orElseThrow();

        // 보낸 메시지 내용
        ChatMessage msg = ChatMessage.builder()
                .user(fromUser)
                .content(dto.getMessage())
                .roomId(dto.getRoomId())
                .createdAt(ZonedDateTime.now())
                .build();

        // 받는 유저와 첫 대화일때
        if(joinRoomRepository.countByRoomId(dto.getRoomId()) != 2) {
            ChatRoom myRoom = ChatRoom.builder()
                    .myUserId(dto.getMyId())
                    .toUserId(dto.getToUserId())
                    .toUserNick(toUser.getNickname())
                    .toUserImg(toUser.getProfileImage())
                    .roomId(dto.getRoomId())
                    .isRead(true)
                    .lastMsg(dto.getMessage())
                    .lastMsgTime(msg.getCreatedAt())
                    .build();
            joinRoomRepository.save(myRoom);

            ChatRoom toUserRoom = ChatRoom.builder()
                    .myUserId(dto.getToUserId())
                    .toUserId(dto.getMyId())
                    .toUserNick(fromUser.getNickname())
                    .toUserImg(fromUser.getProfileImage())
                    .roomId(dto.getRoomId())
                    .isRead(false)
                    .lastMsg(dto.getMessage())
                    .lastMsgTime(msg.getCreatedAt())
                    .build();
            joinRoomRepository.save(toUserRoom);
            chatRepository.save(msg);
            // 기존의 대화가 있을때
        } else {
            ChatRoom myRoom = joinRoomRepository.findByRoomIdAndMyUserId(dto.getRoomId(), dto.getMyId());
            ChatRoom toUserRoom = joinRoomRepository.findByRoomIdAndMyUserId(dto.getRoomId(), dto.getToUserId());

            ChatRoom updateMyRoom = ChatRoom.builder()
                    .id(myRoom.getId())
                    .myUserId(myRoom.getMyUserId())
                    .toUserId(myRoom.getToUserId())
                    .toUserNick(myRoom.getToUserNick())
                    .toUserImg(myRoom.getToUserImg())
                    .roomId(myRoom.getRoomId())
                    .isRead(true)
                    .lastMsg(dto.getMessage())
                    .lastMsgTime(msg.getCreatedAt())
                    .build();
            joinRoomRepository.save(updateMyRoom);

            ChatRoom updateUserRoom = ChatRoom.builder()
                    .id(toUserRoom.getId())
                    .myUserId(toUserRoom.getMyUserId())
                    .toUserId(toUserRoom.getToUserId())
                    .toUserNick(toUserRoom.getToUserNick())
                    .toUserImg(toUserRoom.getToUserImg())
                    .roomId(toUserRoom.getRoomId())
                    .isRead(false)
                    .lastMsg(dto.getMessage())
                    .lastMsgTime(msg.getCreatedAt())
                    .build();
            joinRoomRepository.save(updateUserRoom);

            chatRepository.save(msg);
        }


    }
}
