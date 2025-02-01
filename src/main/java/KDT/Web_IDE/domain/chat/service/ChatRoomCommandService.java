package KDT.Web_IDE.domain.chat.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import KDT.Web_IDE.domain.board.entity.Board;
import KDT.Web_IDE.domain.chat.entity.ChatRoom;
import KDT.Web_IDE.domain.chat.entity.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ChatRoomCommandService {

  private final ChatRoomRepository chatRoomRepository;

  public ChatRoom creatChatRoom(Board board) {
    return chatRoomRepository.save(ChatRoom.builder().board(board).build());
  }
}
