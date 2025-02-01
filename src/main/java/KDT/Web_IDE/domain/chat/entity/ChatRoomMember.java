package KDT.Web_IDE.domain.chat.entity;

import jakarta.persistence.*;

import org.hibernate.annotations.ColumnDefault;

import KDT.Web_IDE.domain.member.entity.Member;
import lombok.*;

@Entity
@Table(name = "chat_room_member")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatRoomMember {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "chat_room_member_id")
  private Long id;

  @Setter
  @Column(name = "not_read_count", nullable = false)
  @ColumnDefault("0")
  @Builder.Default
  private Integer notReadCount = 0;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "chat_room_id")
  private ChatRoom chatRoom;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id")
  private Member member;
}
