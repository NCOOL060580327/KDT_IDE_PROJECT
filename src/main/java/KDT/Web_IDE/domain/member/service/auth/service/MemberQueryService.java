package KDT.Web_IDE.domain.member.service.auth.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import KDT.Web_IDE.domain.member.entity.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberQueryService {

  private final MemberRepository memberRepository;

  // 이메일 중복 검사, 없으면 true, 있으면 false
  public Boolean isValidEmail(String email) {
    return memberRepository.findByEmail(email).isEmpty();
  }

  // 닉네임 중복 검사, 없으면 true, 있으면 false
  public Boolean isValidNickname(String nickname) {
    return memberRepository.findByNickName(nickname).isEmpty();
  }
}
