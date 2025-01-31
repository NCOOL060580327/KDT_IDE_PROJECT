package KDT.Web_IDE.domain.member.service.auth.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import KDT.Web_IDE.domain.member.entity.Member;
import KDT.Web_IDE.domain.member.entity.repository.MemberRepository;
import KDT.Web_IDE.global.exception.GlobalErrorCode;
import KDT.Web_IDE.global.exception.custom.MemberException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberQueryService {

  private final MemberRepository memberRepository;

  public void isValidEmail(String email) {
    if (memberRepository.existsByEmail(email)) {
      throw new MemberException(GlobalErrorCode.DUPLICATE_EMAIL);
    }
  }

  public void isValidNickName(String nickname) {
    if (memberRepository.existsByNickName(nickname)) {
      throw new MemberException(GlobalErrorCode.DUPLICATE_NICKNAME);
    }
  }

  public Member getMemberById(Long memberId) {
    return memberRepository
        .findById(memberId)
        .orElseThrow(() -> new MemberException(GlobalErrorCode.MEMBER_NOT_FOUND));
  }

  public Member getMemberByEmail(String email) {
    return memberRepository
        .findByEmail(email)
        .orElseThrow(() -> new MemberException(GlobalErrorCode.MEMBER_NOT_FOUND));
  }
}
