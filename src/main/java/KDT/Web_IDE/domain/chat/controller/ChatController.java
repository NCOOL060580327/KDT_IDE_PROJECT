package KDT.Web_IDE.domain.chat.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import KDT.Web_IDE.domain.chat.dto.response.GetChatMessageResponseDto;
import KDT.Web_IDE.domain.chat.facade.ChatFacade;
import KDT.Web_IDE.global.response.BaseResponse;
import KDT.Web_IDE.global.security.domain.MemberDetails;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat")
public class ChatController {

  private final ChatFacade chatFacade;

  @GetMapping("/{chatRoomId}")
  public BaseResponse<List<GetChatMessageResponseDto>> getChatMessageList(
      @PathVariable("chatRoomId") Long chatRoomId,
      @AuthenticationPrincipal MemberDetails memberDetails) {
    return BaseResponse.onSuccess(
        chatFacade.getChatMessageList(chatRoomId, Long.valueOf(memberDetails.getUsername())));
  }
}
