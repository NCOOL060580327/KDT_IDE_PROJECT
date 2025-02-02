package KDT.Web_IDE.domain.member.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;

import lombok.*;

@Entity
@Table(
    name = "refresh_token",
    indexes = {@Index(name = "idx_expired_at", columnList = "expired_at")})
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RefreshToken {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "refresh_token_id")
  private Long id;

  @Setter
  @Column(name = "refresh_token")
  private String refreshToken;

  @Column(name = "expired_at", nullable = false)
  private LocalDateTime expiredAt;

  @Column(name = "created_at", nullable = false)
  @Builder.Default
  private LocalDateTime createdAt = LocalDateTime.now();

  @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "member_id")
  private Member member;
}
