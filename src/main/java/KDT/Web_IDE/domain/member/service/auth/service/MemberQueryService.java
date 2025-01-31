package KDT.Web_IDE.domain.member.service.auth.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import KDT.Web_IDE.domain.member.entity.Member;
import KDT.Web_IDE.domain.member.entity.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberQueryService {

  private final MemberRepository memberRepository;

  public Optional<Member> isValidEmail(String email) {
    return memberRepository.findByEmail(email);
  }

  public Optional<Member> isValidNickname(String nickname) {
    return memberRepository.findByNickName(nickname);
  }
}
