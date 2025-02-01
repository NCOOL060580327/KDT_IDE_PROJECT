package KDT.Web_IDE.domain.chat.dto.response;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import KDT.Web_IDE.domain.chat.dto.request.SendChatMessageRequestDto;
import KDT.Web_IDE.domain.chat.entity.ChatMessage;
import KDT.Web_IDE.domain.member.entity.Member;
import lombok.Builder;

@Builder
public record GetChatMessageResponseDto(
    Long senderId, String content, String profileImage, String nickname, String sendTime) {
  public static GetChatMessageResponseDto fromEntity(
      SendChatMessageRequestDto requestDto, Member member) {
    return GetChatMessageResponseDto.builder()
        .senderId(member.getId())
        .content(requestDto.content())
        .profileImage(member.getProfileImage())
        .nickname(member.getNickName())
        .sendTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
        .build();
  }

  public static GetChatMessageResponseDto of(ChatMessage chatMessage, Member member) {
    return GetChatMessageResponseDto.builder()
        .senderId(member.getId())
        .content(chatMessage.getContent())
        .profileImage(member.getProfileImage())
        .nickname(member.getNickName())
        .sendTime(chatMessage.getSendTime().toString())
        .build();
  }
}
