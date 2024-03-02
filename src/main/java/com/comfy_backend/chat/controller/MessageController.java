package com.comfy_backend.chat.controller;


import com.comfy_backend.chat.dto.ChatRequestDTO;
import com.comfy_backend.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.Map;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MessageController {
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final ChatService chatService;
    @MessageMapping("/chat/m")
    public void message(Map<String, ChatRequestDTO> body) {
        System.out.println("body : " + body);
        if(body.get("newMessage") != null) {
            System.out.println("newMessage : " + body.get("message"));
            ChatRequestDTO dto = body.get("newMessage");
            System.out.println("new dto : " + dto);
            chatService.createRoom(dto);
            simpMessagingTemplate.convertAndSend("/sub/chat/" + dto.getMyId() + "/m/" + dto.getRoomId()  , dto);
        } else {
            System.out.println("id : " + body.get("message"));
            ChatRequestDTO dto = body.get("message");
            System.out.println("dto : " + dto);
            chatService.createRoom(dto);
            simpMessagingTemplate.convertAndSend("/sub/chat/m/" + dto.getRoomId()  , dto);
        }
    }

}
