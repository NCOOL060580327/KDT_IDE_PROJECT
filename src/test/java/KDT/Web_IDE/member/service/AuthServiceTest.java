package KDT.Web_IDE.member.service;

import static KDT.Web_IDE.member.constant.MemberTestConstant.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import KDT.Web_IDE.domain.member.dto.request.SignUpMemberRequestDto;
import KDT.Web_IDE.domain.member.dto.response.TokenResponseDto;
import KDT.Web_IDE.domain.member.entity.Member;
import KDT.Web_IDE.domain.member.entity.Password;
import KDT.Web_IDE.domain.member.entity.RefreshToken;
import KDT.Web_IDE.domain.member.entity.repository.MemberRepository;
import KDT.Web_IDE.domain.member.entity.repository.RefreshTokenRepository;
import KDT.Web_IDE.domain.member.service.service.AuthService;
import KDT.Web_IDE.global.exception.GlobalErrorCode;
import KDT.Web_IDE.global.exception.custom.AuthException;
import KDT.Web_IDE.global.exception.custom.MemberException;
import KDT.Web_IDE.global.security.provider.JwtProvider;

@ActiveProfiles("dev")
public class AuthServiceTest {

  @InjectMocks private AuthService authService;

  @Mock private MemberRepository memberRepository;

  @Mock private RefreshTokenRepository refreshTokenRepository;

  @Mock private JwtProvider jwtProvider;

  @Mock private BCryptPasswordEncoder bCryptPasswordEncoder;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this); // Mock 객체 초기화
  }

  @Test
  @DisplayName("회원가입을 시도한다.")
  void signUpMember() {
    // give
    SignUpMemberRequestDto requestDto =
        SignUpMemberRequestDto.builder()
            .email(ID.getValue())
            .password(PASSWORD.getValue())
            .nickName(NICKNAME.getValue())
            .profileImage(PROFILE.getValue())
            .build();

    // when
    authService.signUpMember(requestDto);

    // then
    verify(memberRepository, times(1)).save(any(Member.class));
  }

  @Nested
  @DisplayName("로그인을 할 때 이메일과")
  class loginMember {

    Member member = mock(Member.class);
    Password password = mock(Password.class);

    @Test
    @DisplayName("비밀번호가 일치하면 로그인 성공 및 토큰을 반환한다.")
    void loginMemberSuccess() {
      // give
      when(member.getPassword()).thenReturn(password);
      when(password.isSamePassword(PASSWORD.getValue(), bCryptPasswordEncoder)).thenReturn(true);
      when(member.getId()).thenReturn(ID.getValue());
      when(jwtProvider.generateAccessToken(ID.getValue())).thenReturn(TOKEN.getValue());
      when(jwtProvider.generateRefreshToken(ID.getValue())).thenReturn(TOKEN.getValue());

      // when
      TokenResponseDto responseDto = authService.loginMember(member, PASSWORD.getValue());

      // then
      assertNotNull(responseDto);
    }

    @Test
    @DisplayName("비밀번호가 일치하지 않으면 예외를 발생한다.")
    void loginMemberFailWhenPasswordWrong() {
      // give
      when(member.getPassword()).thenReturn(password);
      when(password.isSamePassword(PASSWORD.getValue(), bCryptPasswordEncoder)).thenReturn(false);

      // when
      MemberException memberException =
          assertThrows(
              MemberException.class, () -> authService.loginMember(member, PASSWORD.getValue()));

      // then
      assertEquals(memberException.getMessage(), GlobalErrorCode.PASSWORD_MISMATCH.getMessage());
    }
  }

  @Test
  @DisplayName("refreshToken을 갱신하고 새 토큰을 발급받는다.")
  void reissueSuccess() {
    // give
    Member mockMember = mock(Member.class);
    RefreshToken mockRefreshToken = mock(RefreshToken.class);

    when(mockMember.getId()).thenReturn(ID.getValue());

    // when
    TokenResponseDto responseDto = authService.reissue(mockMember, mockRefreshToken);

    // then
    assertEquals(responseDto.memberId(), ID.getValue());
  }

  @Nested
  @DisplayName("refreshToken을 조회할 때")
  class getRefreshTokenByMemberId {
    @Test
    @DisplayName("회원 아이디를 통해 조회한다.")
    void getRefreshTokenByMemberIdSuccess() {
      // give
      RefreshToken mockRefreshToken = mock(RefreshToken.class);
      when(refreshTokenRepository.findByMember_Id(ID.getValue()))
          .thenReturn(Optional.of(mockRefreshToken));

      // when
      authService.getRefreshTokenByMemberId(ID.getValue());

      // then
      verify(refreshTokenRepository, times(1)).findByMember_Id(ID.getValue());
    }

    @Test
    @DisplayName("없으면 예외를 발생시킨다.")
    void getRefreshTokenByMemberIdThrowException() {
      // give
      when(refreshTokenRepository.findByMember_Id(ID.getValue())).thenReturn(Optional.empty());

      // when
      AuthException authException =
          assertThrows(
              AuthException.class, () -> authService.getRefreshTokenByMemberId(ID.getValue()));

      // then
      assertEquals(authException.getErrorCode(), GlobalErrorCode.INVALID_TOKEN);
    }
  }
}
