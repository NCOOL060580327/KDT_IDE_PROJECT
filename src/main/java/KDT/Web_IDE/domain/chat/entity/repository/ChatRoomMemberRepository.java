package KDT.Web_IDE.domain.chat.entity.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import KDT.Web_IDE.domain.chat.entity.ChatRoomMember;

public interface ChatRoomMemberRepository extends JpaRepository<ChatRoomMember, Long> {

  Optional<ChatRoomMember> findByChatRoom_IdAndMember_Id(Long chatRoomId, Long memberId);

  @Modifying
  @Query(
      """
    UPDATE ChatRoomMember crm
    SET crm.notReadCount = crm.notReadCount + 1
    WHERE crm.chatRoom.id = :chatRoomId
    AND crm.member.id != :senderId
    """)
  void increaseNotReadCount(@Param("chatRoomId") Long chatRoomId, @Param("senderId") Long senderId);

  @Query(
      """
        SELECT crm
        FROM ChatRoomMember crm
        JOIN FETCH crm.member
        WHERE crm.chatRoom.id = :chatRoomId
        AND crm.member.id != :senderId
    """)
  List<ChatRoomMember> getChatRoomMemberListByChatRoomIdAndMemberId(
      @Param("chatRoomId") Long chatRoomId, @Param("senderId") Long senderId);
}
