package KDT.Web_IDE.domain.member.service.auth.service;

import jakarta.validation.Valid;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import KDT.Web_IDE.domain.member.dto.request.SignUpMemberRequestDto;
import KDT.Web_IDE.domain.member.entity.Member;
import KDT.Web_IDE.domain.member.entity.MemberRole;
import KDT.Web_IDE.domain.member.entity.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

  private final MemberRepository memberRepository;

  public void signUpMember(@Valid SignUpMemberRequestDto requestDto) {
    memberRepository.save(new Member().toEntity(requestDto, MemberRole.USER));
  }
}
