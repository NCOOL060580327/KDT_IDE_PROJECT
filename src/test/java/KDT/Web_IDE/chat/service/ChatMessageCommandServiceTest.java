package KDT.Web_IDE.chat.service;

import static KDT.Web_IDE.chat.constant.ChatTestConstant.CONTENT;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;

import KDT.Web_IDE.domain.chat.dto.request.SendChatMessageRequestDto;
import KDT.Web_IDE.domain.chat.entity.ChatMessage;
import KDT.Web_IDE.domain.chat.entity.ChatRoomMember;
import KDT.Web_IDE.domain.chat.entity.repository.ChatMessageRepository;
import KDT.Web_IDE.domain.chat.service.ChatMessageCommandService;

@ActiveProfiles("dev")
public class ChatMessageCommandServiceTest {

  @Mock private ChatMessageRepository chatMessageRepository;

  @InjectMocks private ChatMessageCommandService chatMessageCommandService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  @DisplayName("메세지를 생성한다.")
  void createChatMessageSuccess() {
    // give
    ChatMessage mockChatMessage = mock(ChatMessage.class);
    ChatRoomMember mockChatRoomMember = mock(ChatRoomMember.class);
    SendChatMessageRequestDto requestDto = new SendChatMessageRequestDto(CONTENT.getValue());
    when(chatMessageRepository.save(mockChatMessage)).thenReturn(any(ChatMessage.class));

    // when
    chatMessageCommandService.createChatMessage(mockChatRoomMember, requestDto);

    // then
    verify(chatMessageRepository, times(1)).save(any(ChatMessage.class));
  }
}
