package KDT.Web_IDE.domain.member.service.auth.facade;

import org.springframework.stereotype.Component;

import KDT.Web_IDE.domain.member.dto.request.SignUpMemberRequestDto;
import KDT.Web_IDE.domain.member.service.auth.service.AuthService;
import KDT.Web_IDE.domain.member.service.auth.service.MemberQueryService;
import KDT.Web_IDE.global.exception.GlobalErrorCode;
import KDT.Web_IDE.global.exception.custom.MemberException;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AuthFacade {

  private final AuthService authService;
  private final MemberQueryService memberQueryService;

  public void signUpMember(SignUpMemberRequestDto requestDto) {

    if (!memberQueryService.isValidEmail(requestDto.email())) {
      throw new MemberException(GlobalErrorCode.DUPLICATE_EMAIL);
    }

    if (!memberQueryService.isValidNickname(requestDto.nickName())) {
      throw new MemberException(GlobalErrorCode.DUPLICATE_NICKNAME);
    }

    authService.signUpMember(requestDto);
  }
}
