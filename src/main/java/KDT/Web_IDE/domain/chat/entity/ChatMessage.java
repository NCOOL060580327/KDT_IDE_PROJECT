package KDT.Web_IDE.domain.chat.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Document(collection = "chat_message")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatMessage {

  @Id private String id;

  private String content;

  @CreatedDate private LocalDateTime sendTime;

  private String chatRoomId;

  private String senderId;
}
