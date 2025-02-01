package KDT.Web_IDE.domain.board.entity;

import jakarta.persistence.*;

import lombok.*;

@Entity
@Table(name = "board")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Board {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "board_id")
  private Long id;

  @Setter private String title;

  @Setter
  @Column(name = "user_count")
  @Builder.Default
  private Integer userCount = 1;
}
