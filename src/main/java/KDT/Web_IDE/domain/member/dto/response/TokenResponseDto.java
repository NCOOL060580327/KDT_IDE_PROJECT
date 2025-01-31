package KDT.Web_IDE.domain.member.dto.response;

import lombok.Builder;

@Builder
public record TokenResponseDto(Long memberId, String accessToken, String refreshToken) {

  public static TokenResponseDto of(Long memberId, String accessToken, String refreshToken) {
    return TokenResponseDto.builder()
        .memberId(memberId)
        .accessToken(accessToken)
        .refreshToken(refreshToken)
        .build();
  }
}
