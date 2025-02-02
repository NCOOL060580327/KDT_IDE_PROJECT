package KDT.Web_IDE.domain.member.schedule;

import java.time.LocalDateTime;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import KDT.Web_IDE.domain.member.entity.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class RefreshTokenSchedule {

  private final RefreshTokenRepository refreshTokenRepository;

  @Scheduled(fixedRateString = "${jwt.refresh-token-validity}")
  public void deleteExpiredTokens() {
    LocalDateTime now = LocalDateTime.now();
    refreshTokenRepository.deleteByExpiredAtBefore(now);
    log.info("Deleted at {} ", now);
  }
}
