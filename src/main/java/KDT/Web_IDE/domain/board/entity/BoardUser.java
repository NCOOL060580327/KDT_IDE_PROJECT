package KDT.Web_IDE.domain.board.entity;

import jakarta.persistence.*;

import org.hibernate.annotations.ColumnDefault;

import KDT.Web_IDE.domain.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "board_user")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoardUser {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "board_user_id")
  private Long id;

  @Column(name = "is_leader")
  @ColumnDefault("false")
  private Boolean isLeader;

  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "board_id", nullable = false)
  private Board board;

  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "member_id", nullable = false)
  private Member member;
}
