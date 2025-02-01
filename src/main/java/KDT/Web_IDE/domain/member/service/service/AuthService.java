package KDT.Web_IDE.domain.member.service.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import KDT.Web_IDE.domain.member.dto.request.SignUpMemberRequestDto;
import KDT.Web_IDE.domain.member.dto.response.TokenResponseDto;
import KDT.Web_IDE.domain.member.entity.Member;
import KDT.Web_IDE.domain.member.entity.MemberRole;
import KDT.Web_IDE.domain.member.entity.repository.MemberRepository;
import KDT.Web_IDE.global.exception.GlobalErrorCode;
import KDT.Web_IDE.global.exception.custom.MemberException;
import KDT.Web_IDE.global.security.provider.JwtProvider;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

  private final MemberRepository memberRepository;

  private final JwtProvider jwtProvider;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;

  public void signUpMember(SignUpMemberRequestDto requestDto) {
    memberRepository.save(new Member().toEntity(requestDto, MemberRole.USER));
  }

  public TokenResponseDto loginMember(Member member, String password) {

    if (!(member.getPassword().isSamePassword(password, bCryptPasswordEncoder))) {
      throw new MemberException(GlobalErrorCode.PASSWORD_MISMATCH);
    }

    String accessToken = jwtProvider.generateAccessToken(member.getId());
    String refreshToken = jwtProvider.generateRefreshToken(member.getId());

    updateRefreshToken(member, refreshToken);

    return TokenResponseDto.of(member.getId(), accessToken, refreshToken);
  }

  public TokenResponseDto reissue(Member member) {

    Long memberId = member.getId();

    String newAccessToken = jwtProvider.generateAccessToken(memberId);
    String newRefreshToken = jwtProvider.generateRefreshToken(memberId);

    updateRefreshToken(member, newRefreshToken);

    return TokenResponseDto.of(memberId, newAccessToken, newRefreshToken);
  }

  private void updateRefreshToken(Member member, String refreshToken) {
    member.setRefreshToken(refreshToken);
  }
}
