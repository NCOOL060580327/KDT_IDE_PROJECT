package KDT.Web_IDE.domain.board.entity.repositroy;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import KDT.Web_IDE.domain.board.entity.BoardUser;

public interface BoardUserRepository extends JpaRepository<BoardUser, Long> {
  Optional<BoardUser> findByMember_IdAndBoard_Id(Long memberId, Long boardId);
}
