package KDT.Web_IDE.chat.service;

import static KDT.Web_IDE.chat.constant.ChatTestConstant.ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;

import KDT.Web_IDE.domain.chat.entity.ChatRoom;
import KDT.Web_IDE.domain.chat.entity.repository.ChatRoomRepository;
import KDT.Web_IDE.domain.chat.service.ChatRoomQueryService;
import KDT.Web_IDE.global.exception.GlobalErrorCode;
import KDT.Web_IDE.global.exception.custom.ChatException;

@ActiveProfiles("dev")
public class ChatRoomQueryServiceTest {

  @Mock private ChatRoomRepository chatRoomRepository;

  @InjectMocks private ChatRoomQueryService chatRoomQueryService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Nested
  @DisplayName("채팅방을")
  class getChatRoomById {

    @Test
    @DisplayName("조회한다.")
    void getChatRoomByIdSuccess() {
      // give
      ChatRoom mockChatRoom = mock(ChatRoom.class);
      when(chatRoomRepository.findById(ID.getValue())).thenReturn(Optional.of(mockChatRoom));

      // when
      ChatRoom chatRoom = chatRoomQueryService.getChatRoomById(ID.getValue());

      // then
      assertEquals(mockChatRoom, chatRoom);
    }

    @Test
    @DisplayName("조회 후 없으면 예외를 발생시킨다.")
    void getChatRoomByIdThrowException() {
      // give
      ChatRoom mockChatRoom = mock(ChatRoom.class);
      when(chatRoomRepository.findById(ID.getValue())).thenReturn(Optional.empty());

      // when
      ChatException chatException =
          assertThrows(
              ChatException.class, () -> chatRoomQueryService.getChatRoomById(ID.getValue()));

      // then
      assertEquals(chatException.getErrorCode(), GlobalErrorCode.CHATROOM_NOT_FOUND);
    }
  }
}
