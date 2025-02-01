package KDT.Web_IDE.domain.chat.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import KDT.Web_IDE.domain.chat.entity.ChatRoom;
import KDT.Web_IDE.domain.chat.entity.repository.ChatRoomRepository;
import KDT.Web_IDE.global.exception.GlobalErrorCode;
import KDT.Web_IDE.global.exception.custom.ChatException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatRoomQueryService {

  private final ChatRoomRepository chatRoomRepository;

  public ChatRoom getChatRoomById(Long chatRoomId) {
    return chatRoomRepository
        .findById(chatRoomId)
        .orElseThrow(() -> new ChatException(GlobalErrorCode.CHATROOM_NOT_FOUND));
  }
}
