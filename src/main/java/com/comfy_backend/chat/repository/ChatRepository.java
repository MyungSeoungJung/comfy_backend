package com.comfy_backend.chat.repository;

import com.comfy_backend.chat.entity.ChatMessage;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<ChatMessage,Long> {
}