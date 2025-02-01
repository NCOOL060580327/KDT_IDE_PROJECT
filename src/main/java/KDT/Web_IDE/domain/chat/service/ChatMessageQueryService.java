package KDT.Web_IDE.domain.chat.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import KDT.Web_IDE.domain.chat.dto.response.GetChatMessageResponseDto;
import KDT.Web_IDE.domain.chat.entity.repository.ChatMessageRepository;
import KDT.Web_IDE.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatMessageQueryService {

  private final ChatMessageRepository chatMessageRepository;

  public List<GetChatMessageResponseDto> getChatMessageList(Long chatRoomId, Member member) {
    return chatMessageRepository
        .getChatMessageByChatRoomIdAndMemberId(chatRoomId, member.getId())
        .stream()
        .map(chatMessage -> GetChatMessageResponseDto.of(chatMessage, member))
        .toList();
  }
}
