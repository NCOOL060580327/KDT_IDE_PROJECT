package KDT.Web_IDE.domain.chat.entity.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import KDT.Web_IDE.domain.chat.entity.ChatMessage;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

  @Query(
      """
    SELECT cm
    FROM ChatMessage cm
    WHERE cm.chatRoomMember.chatRoom.id = :chatRoomId
    AND cm.chatRoomMember.member.id = :memberId
    ORDER BY cm.sendTime DESC
    """)
  List<ChatMessage> getChatMessageByChatRoomIdAndMemberId(
      @Param("chatRoomId") Long chatRoomId, @Param("memberId") Long memberId);
}
