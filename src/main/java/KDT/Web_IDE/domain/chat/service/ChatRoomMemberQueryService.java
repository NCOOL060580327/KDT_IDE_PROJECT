package KDT.Web_IDE.domain.chat.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import KDT.Web_IDE.domain.chat.entity.ChatRoomMember;
import KDT.Web_IDE.domain.chat.entity.repository.ChatRoomMemberRepository;
import KDT.Web_IDE.global.exception.GlobalErrorCode;
import KDT.Web_IDE.global.exception.custom.ChatException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatRoomMemberQueryService {

  private final ChatRoomMemberRepository chatRoomMemberRepository;

  public ChatRoomMember getChatRoomMemberByChatRoomIdAndMemberId(Long chatRoomId, Long memberId) {
    return chatRoomMemberRepository
        .findByChatRoom_IdAndMember_Id(chatRoomId, memberId)
        .orElseThrow(() -> new ChatException(GlobalErrorCode.NOT_IN_CHATROOM));
  }

  public List<ChatRoomMember> getChatRoomMemberListByChatRoomIdAndMemberId(
      Long chatRoomId, Long memberId) {
    return chatRoomMemberRepository.getChatRoomMemberListByChatRoomIdAndMemberId(
        chatRoomId, memberId);
  }
}
