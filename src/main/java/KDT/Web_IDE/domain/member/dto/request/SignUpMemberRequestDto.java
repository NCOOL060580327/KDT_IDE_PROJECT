package KDT.Web_IDE.domain.member.dto.request;

import lombok.Builder;

@Builder
public record SignUpMemberRequestDto(
    String email, String password, String nickName, String profileImage) {}
