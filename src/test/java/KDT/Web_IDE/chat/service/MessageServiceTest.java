package KDT.Web_IDE.chat.service;

import static KDT.Web_IDE.chat.constant.ChatTestConstant.ID;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.test.context.ActiveProfiles;

import KDT.Web_IDE.domain.chat.dto.request.SendChatMessageRequestDto;
import KDT.Web_IDE.domain.chat.dto.response.GetChatMessageResponseDto;
import KDT.Web_IDE.domain.chat.dto.response.UnreadMessageCountResponseDto;
import KDT.Web_IDE.domain.chat.entity.ChatRoom;
import KDT.Web_IDE.domain.chat.entity.ChatRoomMember;
import KDT.Web_IDE.domain.chat.service.MessageService;
import KDT.Web_IDE.domain.member.entity.Member;

@ActiveProfiles("dev")
public class MessageServiceTest {

  @Mock private SimpMessagingTemplate simpMessagingTemplate;

  @InjectMocks private MessageService messageService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  @DisplayName("메세지를 전송한다.")
  void sendMessageSuccess() {
    // give
    SendChatMessageRequestDto requestDto = mock(SendChatMessageRequestDto.class);
    Member mockMember = mock(Member.class);

    // when
    messageService.sendChat(requestDto, ID.getValue(), mockMember);

    // then
    verify(simpMessagingTemplate, times(1))
        .convertAndSend(eq("/room/" + ID.getValue()), any(GetChatMessageResponseDto.class));
  }

  @Test
  @DisplayName("안 읽은 메세지의 수를 전송한다.")
  void sendUnreadMessageCountSuccess() {
    // give
    Member mockMember = mock(Member.class);
    ChatRoom mockChatRoom = mock(ChatRoom.class);
    ChatRoomMember mockChatRoomMember = mock(ChatRoomMember.class);

    List<ChatRoomMember> mockChatRoomMemberList = List.of(mockChatRoomMember);

    when(mockChatRoomMember.getMember()).thenReturn(mockMember);
    when(mockMember.getId()).thenReturn(ID.getValue());
    when(mockChatRoomMember.getChatRoom()).thenReturn(mockChatRoom);
    when(mockChatRoom.getId()).thenReturn(ID.getValue());

    // when
    messageService.sendUnreadMessageCount(mockChatRoomMemberList);

    // then
    verify(simpMessagingTemplate, times(mockChatRoomMemberList.size()))
        .convertAndSend(
            eq("/room/unread/" + ID.getValue()), any(UnreadMessageCountResponseDto.class));
  }
}
