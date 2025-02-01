package KDT.Web_IDE.domain.chat.entity.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import KDT.Web_IDE.domain.chat.entity.ChatMessage;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {}
