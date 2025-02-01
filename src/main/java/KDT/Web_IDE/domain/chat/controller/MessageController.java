package KDT.Web_IDE.domain.chat.controller;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

import KDT.Web_IDE.domain.chat.dto.request.SendChatMessageRequestDto;
import KDT.Web_IDE.domain.chat.facade.ChatFacade;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MessageController {

  private final ChatFacade chatFacade;

  @MessageMapping("/chat/{chatRoomId}")
  public void sendMessage(
      @DestinationVariable("chatRoomId") Long chatRoomId,
      @Payload SendChatMessageRequestDto requestDto) {
    chatFacade.sendMessage(chatRoomId, requestDto);
  }
}
