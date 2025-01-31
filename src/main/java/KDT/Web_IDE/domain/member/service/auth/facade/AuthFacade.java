package KDT.Web_IDE.domain.member.service.auth.facade;

import org.springframework.stereotype.Component;

import KDT.Web_IDE.domain.member.dto.request.LoginMemberRequestDto;
import KDT.Web_IDE.domain.member.dto.request.SignUpMemberRequestDto;
import KDT.Web_IDE.domain.member.dto.response.TokenResponseDto;
import KDT.Web_IDE.domain.member.entity.Member;
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

    memberQueryService
        .isValidEmail(requestDto.email())
        .ifPresent(
            e -> {
              throw new MemberException(GlobalErrorCode.DUPLICATE_EMAIL);
            });

    memberQueryService
        .isValidNickname(requestDto.nickName())
        .ifPresent(
            e -> {
              throw new MemberException(GlobalErrorCode.DUPLICATE_NICKNAME);
            });

    authService.signUpMember(requestDto);
  }

  public TokenResponseDto loginMember(LoginMemberRequestDto requestDto) {

    Member member =
        memberQueryService
            .isValidEmail(requestDto.email())
            .orElseThrow(() -> new MemberException(GlobalErrorCode.MEMBER_NOT_FOUND));

    return authService.loginMember(member, requestDto.password());
  }
}
