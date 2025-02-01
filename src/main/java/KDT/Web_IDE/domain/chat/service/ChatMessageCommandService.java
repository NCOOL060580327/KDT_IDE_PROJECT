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

  public ChatMessage createChatMessage(
      ChatRoomMember chatRoomMember, SendChatMessageRequestDto requestDto) {
    return chatMessageRepository.save(
        ChatMessage.builder()
            .content(requestDto.content())
            .sendTime(LocalDateTime.now())
            .chatRoomMember(chatRoomMember)
            .build());
  }
}
