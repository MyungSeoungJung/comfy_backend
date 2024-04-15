package com.comfy_backend.chat.controller;


import com.comfy_backend.auth.Auth;
import com.comfy_backend.auth.AuthProfile;
import com.comfy_backend.chat.dto.ChatListResponseDto;
import com.comfy_backend.chat.dto.ChatLogResponseDto;
import com.comfy_backend.chat.dto.ChatRequestDto;
import com.comfy_backend.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@Slf4j
@RestController
public class MessageController {
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final ChatService chatService;
    @MessageMapping("/chat/m")
    public void message(Map<String, ChatRequestDto> body) {
        System.out.println("body : " + body);
        if(body.get("newMessage") != null) {
            System.out.println("newMessage : " + body.get("message"));
            ChatRequestDto dto = body.get("newMessage");
            System.out.println("new dto : " + dto);
            chatService.createRoom(dto);
            simpMessagingTemplate.convertAndSend("/sub/chat/" + dto.getMyId() + "/m/" + dto.getRoomId()  , dto);
        } else {
            System.out.println("id : " + body.get("message"));
            ChatRequestDto dto = body.get("message");
            System.out.println("dto : " + dto);
            chatService.createRoom(dto);
            simpMessagingTemplate.convertAndSend("/sub/chat/m/" + dto.getRoomId()  , dto);
        }
    }

    @Auth
    @GetMapping("/chat/chatList")
    public ChatListResponseDto chatList (@RequestAttribute AuthProfile authProfile){
        return chatService.getList(authProfile.getId());
    }

    @PostMapping("/chat/getChatLog")
    public ChatLogResponseDto getRoom(@RequestBody ChatRequestDto dto) {
        System.out.println("로그 조회성공");
        return chatService.getRoom(dto.getRoomId());
    }
    @Auth
    @PostMapping("/chat/read")
    public void read(@RequestAttribute AuthProfile authProfile, @RequestBody ChatRequestDto dto) {
        System.out.println(dto);
        chatService.isRead(authProfile.getId(), dto.getRoomId());
    }
}
