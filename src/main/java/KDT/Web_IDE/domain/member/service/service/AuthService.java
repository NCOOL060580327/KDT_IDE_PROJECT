package KDT.Web_IDE.domain.member.service.service;

import java.time.LocalDateTime;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import KDT.Web_IDE.domain.member.dto.request.SignUpMemberRequestDto;
import KDT.Web_IDE.domain.member.dto.response.TokenResponseDto;
import KDT.Web_IDE.domain.member.entity.Member;
import KDT.Web_IDE.domain.member.entity.MemberRole;
import KDT.Web_IDE.domain.member.entity.RefreshToken;
import KDT.Web_IDE.domain.member.entity.repository.MemberRepository;
import KDT.Web_IDE.domain.member.entity.repository.RefreshTokenRepository;
import KDT.Web_IDE.global.exception.GlobalErrorCode;
import KDT.Web_IDE.global.exception.custom.AuthException;
import KDT.Web_IDE.global.exception.custom.MemberException;
import KDT.Web_IDE.global.security.provider.JwtProvider;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

  private final MemberRepository memberRepository;
  private final RefreshTokenRepository refreshTokenRepository;

  private final JwtProvider jwtProvider;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;

  private final CacheManager cacheManager;

  public void signUpMember(SignUpMemberRequestDto requestDto) {
    memberRepository.save(new Member().toEntity(requestDto, MemberRole.USER));
  }

  public TokenResponseDto loginMember(Member member, String password) {

    if (!(member.getPassword().isSamePassword(password, bCryptPasswordEncoder))) {
      throw new MemberException(GlobalErrorCode.PASSWORD_MISMATCH);
    }

    String accessToken = jwtProvider.generateAccessToken(member.getId());
    String refreshToken = jwtProvider.generateRefreshToken(member.getId());

    createRefreshToken(member, refreshToken);

    return TokenResponseDto.of(member.getId(), accessToken, refreshToken);
  }

  public TokenResponseDto reissue(Member member, RefreshToken refreshToken) {

    Long memberId = member.getId();

    String newAccessToken = jwtProvider.generateAccessToken(memberId);
    String newRefreshToken = jwtProvider.generateRefreshToken(memberId);

    updateRefreshToken(refreshToken, newRefreshToken);
    cacheManager.getCache("refreshTokens").put(refreshToken.getMember().getId(), refreshToken);

    return TokenResponseDto.of(memberId, newAccessToken, newRefreshToken);
  }

  @Cacheable(value = "refreshTokens", key = "#memberId")
  public RefreshToken getRefreshTokenByMemberId(Long memberId) {
    return refreshTokenRepository
        .findByMember_Id(memberId)
        .orElseThrow(() -> new AuthException(GlobalErrorCode.INVALID_TOKEN));
  }

  private void createRefreshToken(Member member, String token) {
    LocalDateTime expiredAt = jwtProvider.getExpiredAt(token);

    refreshTokenRepository.save(
        RefreshToken.builder().refreshToken(token).expiredAt(expiredAt).member(member).build());
  }

  private void updateRefreshToken(RefreshToken refreshToken, String token) {
    refreshToken.setRefreshToken(token);
  }
}
