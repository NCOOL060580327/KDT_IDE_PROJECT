package KDT.Web_IDE.domain.member.entity;

import jakarta.persistence.*;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import KDT.Web_IDE.domain.member.dto.request.SignUpMemberRequestDto;
import lombok.*;

@Entity
@Table(name = "member")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Member {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "member_id")
  private Long id;

  @Column(nullable = false, unique = true)
  private String email;

  @Column(nullable = false)
  @Embedded
  private Password password;

  private String nickName;

  private String profileImage;

  @Enumerated(EnumType.STRING)
  private MemberRole roles;

  @Setter private String refreshToken;

  public Member toEntity(SignUpMemberRequestDto requestDto, MemberRole roles) {

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    return Member.builder()
        .email(requestDto.email())
        .password(Password.encryptPassword(requestDto.password(), encoder))
        .nickName(requestDto.nickName())
        .profileImage(requestDto.profileImage())
        .roles(roles)
        .build();
  }
}
