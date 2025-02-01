package KDT.Web_IDE.domain.chat.service;

import java.util.List;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import KDT.Web_IDE.domain.chat.dto.request.SendChatMessageRequestDto;
import KDT.Web_IDE.domain.chat.dto.response.GetChatMessageResponseDto;
import KDT.Web_IDE.domain.chat.dto.response.UnreadMessageCountResponseDto;
import KDT.Web_IDE.domain.chat.entity.ChatRoomMember;
import KDT.Web_IDE.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MessageService {

  private final SimpMessagingTemplate simpMessagingTemplate;

  public void sendChat(SendChatMessageRequestDto requestDto, Long chatRoomId, Member member) {
    simpMessagingTemplate.convertAndSend(
        "/room/" + chatRoomId, GetChatMessageResponseDto.fromEntity(requestDto, member));
  }

  public void sendUnreadMessageCount(List<ChatRoomMember> chatRoomMemberList) {

    for (ChatRoomMember chatRoomMember : chatRoomMemberList) {

      Long memberId = chatRoomMember.getMember().getId();
      Long chatRoomId = chatRoomMember.getChatRoom().getId();

      int notReadCount = chatRoomMember.getNotReadCount();

      simpMessagingTemplate.convertAndSend(
          "/room/unread/" + memberId, UnreadMessageCountResponseDto.of(chatRoomId, notReadCount));
    }
  }
}
