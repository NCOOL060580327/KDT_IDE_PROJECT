package KDT.Web_IDE.chat.service;

import static KDT.Web_IDE.chat.constant.ChatTestConstant.ID;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;

import KDT.Web_IDE.domain.board.entity.BoardUser;
import KDT.Web_IDE.domain.chat.entity.ChatRoom;
import KDT.Web_IDE.domain.chat.entity.ChatRoomMember;
import KDT.Web_IDE.domain.chat.entity.repository.ChatRoomMemberRepository;
import KDT.Web_IDE.domain.chat.service.ChatRoomMemberCommandService;

@ActiveProfiles("dev")
public class ChatRoomMemberCommandServiceTest {

  @Mock private ChatRoomMemberRepository chatRoomMemberRepository;

  @InjectMocks private ChatRoomMemberCommandService chatRoomMemberCommandService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  @DisplayName("ChatRoomMember를 생성한다.")
  void createChatRoomMemberSuccess() {
    // give
    BoardUser mockBoardUser = mock(BoardUser.class);
    ChatRoom mockChatRoom = mock(ChatRoom.class);
    ChatRoomMember mockChatRoomMember = mock(ChatRoomMember.class);
    when(chatRoomMemberRepository.save(any(ChatRoomMember.class))).thenReturn(mockChatRoomMember);

    // when
    chatRoomMemberCommandService.createChatRoomMember(mockBoardUser, mockChatRoom);

    // then
    verify(chatRoomMemberRepository, times(1)).save(any(ChatRoomMember.class));
  }

  @Test
  @DisplayName("ChatRoom에 있는 Member의 NotReadCount를 1씩 증가시킨다.")
  void increaseNotReadCountSuccess() {
    // when
    chatRoomMemberCommandService.increaseNotReadCount(ID.getValue(), ID.getValue());

    // then
    verify(chatRoomMemberRepository, times(1)).increaseNotReadCount(ID.getValue(), ID.getValue());
  }

  @Test
  @DisplayName("메세지를 조회한 사용자의 NotReadCount를 0으로 초기화한다.")
  void resetNotReadCountSuccess() {
    // when
    chatRoomMemberCommandService.resetNotReadCount(ID.getValue(), ID.getValue());

    // then
    verify(chatRoomMemberRepository, times(1)).resetNotReadCount(ID.getValue(), ID.getValue());
  }
}
