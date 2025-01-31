package KDT.Web_IDE.domain.member.service.auth.facade;

import org.springframework.stereotype.Component;

import KDT.Web_IDE.domain.member.dto.request.LoginMemberRequestDto;
import KDT.Web_IDE.domain.member.dto.request.ReissueRequestDto;
import KDT.Web_IDE.domain.member.dto.request.SignUpMemberRequestDto;
import KDT.Web_IDE.domain.member.dto.response.TokenResponseDto;
import KDT.Web_IDE.domain.member.entity.Member;
import KDT.Web_IDE.domain.member.service.auth.service.AuthService;
import KDT.Web_IDE.domain.member.service.auth.service.MemberQueryService;
import KDT.Web_IDE.global.security.provider.JwtProvider;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AuthFacade {

  private final AuthService authService;
  private final MemberQueryService memberQueryService;
  private final JwtProvider jwtProvider;

  public void signUpMember(SignUpMemberRequestDto requestDto) {

    memberQueryService.isValidEmail(requestDto.email());

    memberQueryService.isValidNickName(requestDto.nickName());

    authService.signUpMember(requestDto);
  }

  public TokenResponseDto loginMember(LoginMemberRequestDto requestDto) {

    Member member = memberQueryService.getMemberByEmail(requestDto.email());

    return authService.loginMember(member, requestDto.password());
  }

  public TokenResponseDto reissue(ReissueRequestDto requestDto) {

    Long memberId = jwtProvider.getSubject(requestDto.refreshToken());

    Member member = memberQueryService.getMemberById(memberId);

    return authService.reissue(member);
  }
}
