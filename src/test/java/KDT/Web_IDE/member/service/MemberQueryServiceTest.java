package KDT.Web_IDE.member.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;

import KDT.Web_IDE.domain.member.entity.Member;
import KDT.Web_IDE.domain.member.entity.repository.MemberRepository;
import KDT.Web_IDE.domain.member.service.auth.service.MemberQueryService;

@ActiveProfiles("dev")
public class MemberQueryServiceTest {

  @InjectMocks private MemberQueryService memberQueryService;

  @Mock private MemberRepository memberRepository;

  private final String TEST_EMAIL = "test@naver.com";
  private final String TEST_NICKNAME = "test";

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this); // Mock 객체 초기화
  }

  @Nested
  @DisplayName("회원가입을 할 때")
  class authQueryService {

    @Nested
    @DisplayName("이메일을 확인 후")
    class isValidEmail {

      @Test
      @DisplayName("중복되지 않으면 true를 반환한다.")
      void returnTrueWhenEmailDoesNotExist() {
        // give

        // when
        Boolean result = memberQueryService.isValidEmail(TEST_EMAIL).isEmpty();

        // then
        assertTrue(result);
      }

      @Test
      @DisplayName("이미 존재하면 false를 반환한다.")
      void returnFalseWhenEmailDoesExist() {
        // give
        when(memberRepository.findByEmail(TEST_EMAIL)).thenReturn(Optional.of(new Member()));

        // when
        Boolean result = memberQueryService.isValidEmail(TEST_EMAIL).isEmpty();

        // then
        assertFalse(result);
      }
    }

    @Nested
    @DisplayName("닉네임을 확인 후")
    class isValidNickname {

      @Test
      @DisplayName("중복되지 않으면 true를 반환한다.")
      void returnTrueWhenNicknameDoesExist() {
        // give

        // when
        Boolean result = memberQueryService.isValidNickname(TEST_NICKNAME).isEmpty();

        // then
        assertTrue(result);
      }

      @Test
      @DisplayName("이미 존재하면 false를 반환한다.")
      void returnFalseWhenNicknameDoesExist() {
        // give
        when(memberRepository.findByNickName(TEST_NICKNAME)).thenReturn(Optional.of(new Member()));

        // when
        Boolean result = memberQueryService.isValidNickname(TEST_NICKNAME).isEmpty();

        // then
        assertFalse(result);
      }
    }
  }
}
