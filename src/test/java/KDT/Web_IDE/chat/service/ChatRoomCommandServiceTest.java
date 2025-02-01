package KDT.Web_IDE.chat.service;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;

import KDT.Web_IDE.domain.board.entity.Board;
import KDT.Web_IDE.domain.chat.entity.ChatRoom;
import KDT.Web_IDE.domain.chat.entity.repository.ChatRoomRepository;
import KDT.Web_IDE.domain.chat.service.ChatRoomCommandService;

@ActiveProfiles("dev")
public class ChatRoomCommandServiceTest {

  @Mock private ChatRoomRepository chatRoomRepository;

  @InjectMocks private ChatRoomCommandService chatRoomCommandService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  @DisplayName("채팅방을 생성한다.")
  void creatChatRoomSuccess() {
    // give
    ChatRoom mockChatRoom = mock(ChatRoom.class);
    Board mockBoard = mock(Board.class);
    when(chatRoomRepository.save(mockChatRoom)).thenReturn(mockChatRoom);

    // when
    chatRoomCommandService.creatChatRoom(mockBoard);

    // then
    verify(chatRoomRepository, times(1)).save(any(ChatRoom.class));
  }
}
