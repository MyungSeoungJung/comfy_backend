package com.comfy_backend.chat.repository;

import com.comfy_backend.chat.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRepository extends JpaRepository<ChatRoom,Long> {
    Long countByRoomId(Long roomid);
    ChatRoom findByRoomIdAndMyUserId(Long roomid, Long userid);

}
