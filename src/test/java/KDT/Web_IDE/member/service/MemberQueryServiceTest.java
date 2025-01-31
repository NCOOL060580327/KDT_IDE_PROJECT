package KDT.Web_IDE.member.service;

import static KDT.Web_IDE.member.constant.MemberTestConstant.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
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
import KDT.Web_IDE.global.exception.GlobalErrorCode;
import KDT.Web_IDE.global.exception.custom.MemberException;

@ActiveProfiles("dev")
public class MemberQueryServiceTest {

  @InjectMocks private MemberQueryService memberQueryService;

  @Mock private MemberRepository memberRepository;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Nested
  @DisplayName("이메일을 확인 후")
  class isValidEmail {

    @Test
    @DisplayName("중복되지 않으면 true를 반환한다.")
    void returnTrueWhenEmailDoesNotExist() {
      // give
      when(memberRepository.existsByEmail(EMAIL.getValue())).thenReturn(false);

      // then
      assertDoesNotThrow(() -> memberQueryService.isValidEmail(EMAIL.getValue()));
    }

    @Test
    @DisplayName("이미 존재하면 false를 반환한다.")
    void returnFalseWhenEmailDoesExist() {
      // give
      when(memberRepository.existsByEmail(EMAIL.getValue())).thenReturn(true);

      // when
      MemberException memberException =
          assertThrows(
              MemberException.class, () -> memberQueryService.isValidEmail(EMAIL.getValue()));

      // then
      assertEquals(memberException.getMessage(), GlobalErrorCode.DUPLICATE_EMAIL.getMessage());
    }
  }

  @Nested
  @DisplayName("닉네임을 확인 후")
  class isValidNickname {

    @Test
    @DisplayName("중복되지 않으면 true를 반환한다.")
    void returnTrueWhenNicknameDoesExist() {
      // give
      when(memberRepository.existsByNickName(NICKNAME.getValue())).thenReturn(false);

      // then
      assertDoesNotThrow(() -> memberQueryService.isValidNickName(NICKNAME.getValue()));
    }

    @Test
    @DisplayName("이미 존재하면 false를 반환한다.")
    void returnFalseWhenNicknameDoesExist() {
      // give
      when(memberRepository.existsByNickName(NICKNAME.getValue())).thenReturn(true);

      // when
      MemberException memberException =
          assertThrows(
              MemberException.class, () -> memberQueryService.isValidNickName(NICKNAME.getValue()));

      // then
      assertEquals(memberException.getMessage(), GlobalErrorCode.DUPLICATE_NICKNAME.getMessage());
    }
  }

  @Nested
  @DisplayName("회원 ID로 조회 시")
  class getMemberById {

    @Test
    @DisplayName("정상조회한다.")
    void getMemberByIdSuccess() {
      // give
      Member mockMember = mock(Member.class);
      when(memberRepository.findById(ID.getValue())).thenReturn(Optional.of(mockMember));

      // when
      Member member = memberQueryService.getMemberById(ID.getValue());

      // then
      assertEquals(mockMember, member);
    }

    @Test
    @DisplayName("회원이 존재하지 않으면 예외를 발생시킨다.")
    void getMemberByIdThrowException() {
      // give
      when(memberRepository.findById(ID.getValue())).thenReturn(Optional.empty());

      // when
      MemberException memberException =
          assertThrows(
              MemberException.class, () -> memberQueryService.getMemberById(ID.getValue()));

      // then
      assertEquals(memberException.getMessage(), GlobalErrorCode.MEMBER_NOT_FOUND.getMessage());
    }
  }

  @Nested
  @DisplayName("회원 이메일로 조회 시")
  class getMemberByEmail {

    @Test
    @DisplayName("정상조회한다.")
    void getMemberByEmailSuccess() {
      // give
      Member mockMember = mock(Member.class);
      when(memberRepository.findByEmail(EMAIL.getValue())).thenReturn(Optional.of(mockMember));

      // when
      Member member = memberQueryService.getMemberByEmail(EMAIL.getValue());

      // then
      assertEquals(mockMember, member);
    }

    @Test
    @DisplayName("회원이 존재하지 않으면 예외를 발생시킨다.")
    void getMemberByEmailThrowException() {
      // give
      when(memberRepository.findByEmail(EMAIL.getValue())).thenReturn(Optional.empty());

      // when
      MemberException memberException =
          assertThrows(
              MemberException.class, () -> memberQueryService.getMemberByEmail(EMAIL.getValue()));

      // then
      assertEquals(memberException.getMessage(), GlobalErrorCode.MEMBER_NOT_FOUND.getMessage());
    }
  }
}
