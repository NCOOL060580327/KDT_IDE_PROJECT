package KDT.Web_IDE.domain.chat.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import KDT.Web_IDE.domain.board.entity.BoardUser;
import KDT.Web_IDE.domain.chat.entity.ChatRoom;
import KDT.Web_IDE.domain.chat.entity.ChatRoomMember;
import KDT.Web_IDE.domain.chat.entity.repository.ChatRoomMemberRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ChatRoomMemberCommandService {

  private final ChatRoomMemberRepository chatRoomMemberRepository;

  public void createChatRoomMember(BoardUser boardUser, ChatRoom chatRoom) {
    chatRoomMemberRepository.save(
        ChatRoomMember.builder().chatRoom(chatRoom).member(boardUser.getMember()).build());
  }

  public void increaseNotReadCount(Long chatRoomId, Long senderId) {
    chatRoomMemberRepository.increaseNotReadCount(chatRoomId, senderId);
  }
}
