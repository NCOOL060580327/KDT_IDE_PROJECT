package KDT.Web_IDE.domain.member.entity.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import KDT.Web_IDE.domain.member.entity.RefreshToken;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
  Optional<RefreshToken> findByMember_Id(Long memberId);

  @Transactional
  void deleteByExpiredAtBefore(LocalDateTime now);
}
