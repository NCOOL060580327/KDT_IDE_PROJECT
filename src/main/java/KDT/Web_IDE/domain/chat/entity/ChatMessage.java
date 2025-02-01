package KDT.Web_IDE.domain.chat.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;

import org.springframework.data.annotation.CreatedDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "chat_message")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatMessage {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "chat_message_id")
  private Long id;

  @Column(columnDefinition = "LONGTEXT")
  private String content;

  @CreatedDate
  @Column(name = "send_time", updatable = false)
  private LocalDateTime sendTime;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "chat_room_member_id")
  private ChatRoomMember chatRoomMember;
}
