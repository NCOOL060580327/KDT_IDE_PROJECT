package KDT.Web_IDE.member.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
import KDT.Web_IDE.domain.member.entity.repository.MemberRepository;
import KDT.Web_IDE.domain.member.service.auth.service.AuthService;
import KDT.Web_IDE.global.exception.GlobalErrorCode;
import KDT.Web_IDE.global.exception.custom.MemberException;
import KDT.Web_IDE.global.security.provider.JwtProvider;

@ActiveProfiles("dev")
public class AuthServiceTest {

  @InjectMocks private AuthService authService;

  @Mock private MemberRepository memberRepository;

  @Mock private JwtProvider jwtProvider;

  @Mock private BCryptPasswordEncoder bCryptPasswordEncoder;

  private final String TEST_EMAIL = "test";
  private final String TEST_PASSWORD = "Test1234!@#$";
  private final String TEST_NICKNAME = "test";
  private final String TEST_PROFILE = "test";
  private final String TEST_TOKEN = "test";

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
            .email(TEST_EMAIL)
            .password(TEST_PASSWORD)
            .nickName(TEST_NICKNAME)
            .profileImage(TEST_PROFILE)
            .build();

    // when
    authService.signUpMember(requestDto);

    // then
    verify(memberRepository, times(1)).save(any(Member.class));
  }

  @Nested
  @DisplayName("로그인을 할 때 이메일과")
  class loginMember {

    @Test
    @DisplayName("비밀번호가 일치하면 로그인 성공 및 토큰을 반환한다.")
    void loginMemberSuccess() {
      // give
      Member member = mock(Member.class);
      Password password = mock(Password.class);

      when(member.getPassword()).thenReturn(password);
      when(password.isSamePassword(TEST_PASSWORD, bCryptPasswordEncoder)).thenReturn(true);
      when(member.getId()).thenReturn(1L);
      when(jwtProvider.generateAccessToken(1L)).thenReturn(TEST_TOKEN);
      when(jwtProvider.generateRefreshToken(1L)).thenReturn(TEST_TOKEN);

      // when
      TokenResponseDto responseDto = authService.loginMember(member, TEST_PASSWORD);

      // then
      assertNotNull(responseDto);
    }

    @Test
    @DisplayName("비밀번호가 일치하지 않으면 예외를 발생한다.")
    void loginMemberFailWhenPasswordWrong() {
      // give
      Member member = mock(Member.class);
      Password password = mock(Password.class);

      when(member.getPassword()).thenReturn(password);
      when(password.isSamePassword(TEST_PASSWORD, bCryptPasswordEncoder)).thenReturn(false);

      // when
      MemberException memberException =
          assertThrows(MemberException.class, () -> authService.loginMember(member, TEST_PASSWORD));

      // then
      assertEquals(memberException.getMessage(), GlobalErrorCode.PASSWORD_MISMATCH.getMessage());
    }
  }

  @Test
  @DisplayName("RefreshToken을 업데이트한다.")
  void updateRefreshToken() {
    // give
    Member member = mock(Member.class);

    // when
    authService.updateRefreshToken(member, TEST_TOKEN);

    // then
    verify(member, times(1)).setRefreshToken(TEST_TOKEN);
  }
}
