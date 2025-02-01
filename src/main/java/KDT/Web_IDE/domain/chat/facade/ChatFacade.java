package KDT.Web_IDE.domain.chat.facade;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import KDT.Web_IDE.domain.chat.dto.request.SendChatMessageRequestDto;
import KDT.Web_IDE.domain.chat.dto.response.GetChatMessageResponseDto;
import KDT.Web_IDE.domain.chat.entity.ChatRoomMember;
import KDT.Web_IDE.domain.chat.service.*;
import KDT.Web_IDE.domain.member.entity.Member;
import KDT.Web_IDE.domain.member.service.service.MemberQueryService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ChatFacade {

  private final ChatMessageCommandService chatMessageCommandService;
  private final ChatMessageQueryService chatMessageQueryService;
  private final ChatRoomQueryService chatRoomQueryService;
  private final ChatRoomMemberCommandService chatRoomMemberCommandService;
  private final ChatRoomMemberQueryService chatRoomMemberQueryService;
  private final MessageService messageService;

  private final MemberQueryService memberQueryService;

  public void sendMessage(Long chatRoomId, SendChatMessageRequestDto requestDto) {

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    chatRoomQueryService.getChatRoomById(chatRoomId);

    Member member = memberQueryService.getMemberById(Long.valueOf(authentication.getName()));

    ChatRoomMember chatRoomMember =
        chatRoomMemberQueryService.getChatRoomMemberByChatRoomIdAndMemberId(
            chatRoomId, member.getId());

    messageService.sendChat(requestDto, chatRoomId, member);

    chatMessageCommandService.createChatMessage(chatRoomMember, requestDto);

    chatRoomMemberCommandService.increaseNotReadCount(chatRoomId, member.getId());

    List<ChatRoomMember> chatRoomList =
        chatRoomMemberQueryService.getChatRoomMemberListByChatRoomIdAndMemberId(
            chatRoomId, member.getId());

    messageService.sendUnreadMessageCount(chatRoomList);
  }

  public List<GetChatMessageResponseDto> getChatMessageList(Long chatRoomId, Long memberId) {

    chatRoomQueryService.getChatRoomById(chatRoomId);

    Member member = memberQueryService.getMemberById(memberId);

    List<GetChatMessageResponseDto> responseDtoList =
        chatMessageQueryService.getChatMessageList(chatRoomId, member);

    chatRoomMemberCommandService.resetNotReadCount(chatRoomId, memberId);

    return responseDtoList;
  }
}
