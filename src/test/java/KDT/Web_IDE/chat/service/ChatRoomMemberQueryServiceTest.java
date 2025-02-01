package KDT.Web_IDE.chat.service;

import static KDT.Web_IDE.chat.constant.ChatTestConstant.ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;

import KDT.Web_IDE.domain.chat.entity.ChatRoomMember;
import KDT.Web_IDE.domain.chat.entity.repository.ChatRoomMemberRepository;
import KDT.Web_IDE.domain.chat.service.ChatRoomMemberQueryService;
import KDT.Web_IDE.global.exception.GlobalErrorCode;
import KDT.Web_IDE.global.exception.custom.ChatException;

@ActiveProfiles("dev")
public class ChatRoomMemberQueryServiceTest {

  @Mock private ChatRoomMemberRepository chatRoomMemberRepository;

  @InjectMocks private ChatRoomMemberQueryService chatRoomMemberQueryService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Nested
  @DisplayName("채팅방과 회원을 통해 ChatRoomMember을 조회하고")
  class getChatRoomMemberByChatRoomIdAndMemberId {
    @Test
    @DisplayName("반환하다.")
    void getChatRoomMemberByChatRoomIdAndMemberIdSuccess() {
      // give
      ChatRoomMember mockChatRoomMember = mock(ChatRoomMember.class);
      when(chatRoomMemberRepository.findByChatRoom_IdAndMember_Id(ID.getValue(), ID.getValue()))
          .thenReturn(Optional.of(mockChatRoomMember));

      // when
      ChatRoomMember chatRoomMember =
          chatRoomMemberQueryService.getChatRoomMemberByChatRoomIdAndMemberId(
              ID.getValue(), ID.getValue());

      // then
      assertEquals(mockChatRoomMember, chatRoomMember);
    }

    @Test
    @DisplayName("없으면 예외를 발생시킨다.")
    void getChatRoomMemberByChatRoomIdAndMemberIdThrowException() {
      // give
      when(chatRoomMemberRepository.findByChatRoom_IdAndMember_Id(ID.getValue(), ID.getValue()))
          .thenReturn(Optional.empty());

      // when
      ChatException chatException =
          assertThrows(
              ChatException.class,
              () ->
                  chatRoomMemberQueryService.getChatRoomMemberByChatRoomIdAndMemberId(
                      ID.getValue(), ID.getValue()));

      // then
      assertEquals(chatException.getErrorCode(), GlobalErrorCode.NOT_IN_CHATROOM);
    }
  }

  @Test
  @DisplayName("채팅방과 회원을 통해 ChatRoomMemberList를 조회하고 리스트를 반환한다")
  void getChatRoomMemberByChatRoomIdAndMemberIdSuccess() {
    // give
    List<ChatRoomMember> mockChatRoomMemberList = List.of(mock(ChatRoomMember.class));
    when(chatRoomMemberRepository.getChatRoomMemberListByChatRoomIdAndMemberId(
            ID.getValue(), ID.getValue()))
        .thenReturn(mockChatRoomMemberList);

    // when
    List<ChatRoomMember> chatRoomMemberList =
        chatRoomMemberQueryService.getChatRoomMemberListByChatRoomIdAndMemberId(
            ID.getValue(), ID.getValue());

    // then
    assertEquals(mockChatRoomMemberList, chatRoomMemberList);
  }
}
