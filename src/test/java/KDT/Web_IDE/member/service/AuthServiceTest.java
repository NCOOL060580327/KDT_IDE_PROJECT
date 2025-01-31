package KDT.Web_IDE.member.service;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;

import KDT.Web_IDE.domain.member.dto.request.SignUpMemberRequestDto;
import KDT.Web_IDE.domain.member.entity.Member;
import KDT.Web_IDE.domain.member.entity.repository.MemberRepository;
import KDT.Web_IDE.domain.member.service.auth.service.AuthService;

@ActiveProfiles("dev")
public class AuthServiceTest {

  @InjectMocks private AuthService authService;

  @Mock private MemberRepository memberRepository;

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
            .email("test")
            .password("Test1234!@#$")
            .nickName("test")
            .profileImage("test")
            .build();

    // when
    authService.signUpMember(requestDto);

    // then
    verify(memberRepository, times(1)).save(any(Member.class));
  }
}
