package KDT.Web_IDE.domain.chat.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import KDT.Web_IDE.domain.chat.dto.request.SendChatMessageRequestDto;
import KDT.Web_IDE.domain.chat.entity.ChatMessage;
import KDT.Web_IDE.domain.chat.entity.ChatRoomMember;
import KDT.Web_IDE.domain.chat.entity.repository.ChatMessageRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ChatMessageCommandService {

  private final ChatMessageRepository chatMessageRepository;

  public void createChatMessage(
      ChatRoomMember chatRoomMember, SendChatMessageRequestDto requestDto) {
    chatMessageRepository.save(
        ChatMessage.builder()
            .content(requestDto.content())
            .sendTime(LocalDateTime.now())
            .chatRoomId(chatRoomMember.getChatRoom().getId().toString())
            .senderId(chatRoomMember.getMember().getId().toString())
            .build());
  }
}
