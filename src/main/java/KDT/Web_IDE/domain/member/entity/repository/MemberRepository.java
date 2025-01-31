package KDT.Web_IDE.domain.member.entity.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import KDT.Web_IDE.domain.member.entity.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

  Optional<Member> findByEmail(String email);

  Optional<Member> findByNickName(String nickName);

  Boolean existsByEmail(String email);

  Boolean existsByNickName(String nickName);
}
