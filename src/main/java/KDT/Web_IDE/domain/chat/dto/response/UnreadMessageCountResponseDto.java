package KDT.Web_IDE.domain.chat.dto.response;

import lombok.Builder;

@Builder
public record UnreadMessageCountResponseDto(Long chatRoomId, Integer notReadCount) {
  public static UnreadMessageCountResponseDto of(Long chatRoomId, Integer notReadCount) {
    return UnreadMessageCountResponseDto.builder()
        .chatRoomId(chatRoomId)
        .notReadCount(notReadCount)
        .build();
  }
}
